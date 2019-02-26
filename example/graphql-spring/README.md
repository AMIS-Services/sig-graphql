# Spring + GraphQL

This is the Spring-Boot project for the GraphQL SIG. It is the java alternative to javascripts express/node option to expose the resources necessary for the React front-end. In this track you will add configurations and write several classes to implement a graphQL resource.

## Getting started

This project requires java 8.

See src/main/resources/config/application.yml for the current datasource configuration; it expects a postgresql database at port 5432 with password "postgress_password". You may supply this database with the following docker-command: "docker run -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres_password --name graphqlPostgres postgres" (same as "yarn start-db" in the express track).
NB you may use a different database by changing the datasource configuration accordingly. Note that liquibase (src/main/resources/config/liquibase/db.changelog.xml) and the Domain Access Objects (src/main/java/nl/amis/sig/graphql/domain) are configured with camelCase in mind.

In order to run the project, run the following commands in the graphql-spring root folder:

- `./mvnw install`
- `./mvnw spring-boot:run` or `./mvnw`, which runs the previous command by default

Run the project and try some get-requests with Postman:

- `localhost:3030/api/people`
- `localhost:3030/api/practices`
- `localhost:3030/api/projects`

## Hands on

### Step 1: Hello world

After you get the project running, you can start the transformation to graphQL by including the following dependencies:

```xml
<!--GraphQL-Java Tools - Loads and powers our GraphQL schema-->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-java-tools</artifactId>
    <version>4.3.0</version>
</dependency>
<!--GraphQL-Java Spring boot starter for GraphQL - hosts our schema at the /graphql endpoint in our spring context-->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-spring-boot-starter</artifactId>
    <version>4.0.0</version>
</dependency>
<!--GraphQL-Java Spring boot starter for GraphIQL - a web based UI for interacting with the /graphql endpoint, with knowledge of the schema at the endpoint-->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphiql-spring-boot-starter</artifactId>
    <version>4.0.0</version>
</dependency>
```

The application is configured to search for graphql schema files on the classpath with extension `.graphqls`. Let's add a simple schema file with the following content:

```javascript
type Query {
    hello: String
}
```

This schema defines the `hello` query, which returns a String.

The application is configured to search for graphql resolvers on the classpath, java classes that implement `GraphQLQueryResolver` and are annotated with `@Component`. This is called a `QueryResolver`. You may have multiple `QueryResolver`s, but the queries defined in the graphql schema must map to exactly one `QueryResolver`. 
Create a new java class that implements `GraphQLQueryResolver`, is annotated with `@Component` and has a public method called `hello()` returning a String - for instance "Hello world!".

Now run the application and try the hello-query.
In Postman you could do the following POST to `http://localhost:3030/graphql`:
```javascript
{
	"query": "{ hello }"
}
``` 
Alternatively use graphiQL in the browser at `http://localhost:3030/graphiql`. This is a web based UI for interacting with the /graphql endpoint. It has basic knowledge of the graphQL schema and can help writing queries. Note that it does not support the same json input, but a simplified version (not valid json). Use for example:
```javascript
query {
	hello
}
``` 

### Step 2: Expanding the schema

Being able to return static Strings is nice, but it does not satisfy the desire to replace the traditional REST api. Ultimately we want queries that return `people`, `practices` and `projects`. These will be queries, just like the `hello` query in the previous step. Unlike the `hello` query, these queries will return objects.

Graphql is typed with basic types such as `Int`, `Float`, `String` and `Boolean` and the possibility to define your own types. The queries mentioned before will be of these custom types.
Let's add the `Person` type:
```javascript
type Person {
    id: Int!
    name: String!
    createdAt: String
    updatedAt: String
    practice: Practice
    projects: [Project]
}
```
Notice that the createdAt and updatedAt properties are Strings; graphql does not have a Date type! The exclamation marks mean that the field is non-nullable, which will be relevant later, for the mutations.

Let's add the `people` query with type `[Person]` (the brackets indicate an Array). By adding this query to the schema, you are required to add it as `QueryResolver` as well. For now you can return a simple `List<Person>` object:
```java
Person person1 = new Person();
person1.setId(1);
person1.setName("Henk");

List<Person> people = new ArrayList<Person>();
people.add(person1);

return people;
```

Try querying `people`. You may get an exception "`Sub selection required`". If you do, that's because graphql requires you to specify the fields of `Person` you want to get (between curly brackets).

### Step 3: Returning actual data

In the previous steps you created a `QueryResolver` for `people`. Add a constructor to the `QueryResolver` and `Autowire` the `PersonRepository`:
```java
private PersonRepository personRepository;
public Query(PersonRepository personRepository) {
    this.personRepository = personRepository;
}
```
Notice that the `@Autowired` annotation is not used; this is not required when constructors use classes that are annotated with `@Repository` (or any `@Component` annotation).

Change the implementation of the `people` method to return the `getPeople()` results of the service (or the `findAll()` results of the repository).

Try querying `people` again. Do you get a list of Person?

### Step 4: Inputs

Like traditional REST, it is often desired to find the `person` with `id`. To do so, you need to change the schema and add a parameter to the `people` query:
```javascript
people(id: Int): [Person]
```
Like before, you also need to change the `QueryResolver`. Note: you need to return a `List<Person>`, as described in the schema - this can be a List with a single element! If you want to return a `Person`, you need to create a `person` query.

Try querying `people` with id: 1.
Use the following query (from Postman):
```javascript
{
	"query": "{ people(id: 1) { id name } }"
}
```
Did you find `Kjettil`?

### Step 5: Writing a new resolver

Create queries for `practices` and `projects`, this time with different inputs. For `practices` we want to search using an array of `ids` and for `projects` we want to search by `name`.
Use the following queries (from Postman):
```javascript
{
	"query": "{ practices(ids: [1, 2]) { id name } projects(name: \"CIS\") { id name } }"
}
```
Notice that both the `practices` and `projects` queries are called, the `practices` query is called with the ids List `[1, 2]` and the `projects` query is called with name String `\"CIS\"`, where the backslashes are used to escape the double quotes.

### Step 6: Nested resolvers

At this point you effectively rewrote the `/people` (step 3), `/practices` (step 5) and `/projects` (step 5) resources. In step 4 you rewrote the `/people/{id}` resource and you even created resources that would require specific query parameters. However, compared to traditional REST this barely has any advantages - you could even say this is more complex!
The real advantage of graphql over traditional REST is how details of entities are obtained. To implement this, you need to add a `FieldResolver` - a java class that implements `GraphQLResolver<Entity>` - with methods for each queryable attribute. For example:
```java
@Component
@Transactional
public class PersonResolver implements GraphQLResolver<Person> {

    private PracticeRepository practiceRepository;
    private ProjectRepository projectRepository;

    public PersonResolver(PracticeRepository practiceRepository, ProjectRepository projectRepository) {
        this.practiceRepository = practiceRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Practice> getPractice(Person person) {
        return practiceRepository.findByPeople_Id(person.getId());
    }

    @Transactional(readOnly = true)
    public List<Project> getProjects(Person person) {
        return projectRepository.findByPeople_Id(person.getId());
    }
}
```
Notice the `findBy`-statements on the repositories. These statements use the `@ManyToMany` relation between `people` and `practices` and `projects` respectively.

Add `EntityResolver`s for the entities `Practice` and `Project` as well.

Now try querying the name and practice for all people and for each practice get its name and the name of all related projects 

### Step 7: To the front-end!

Now that we have a nice resolver with nested subresolvers we have everything we need to update our front-end practices component from REST to GraphQL. Go on over to the README in the react folder and get started.

### Step 8: Mutations

In the previous steps you created a fully operational graphql resource that allows queries on all entities and their properties. Essentially these are the `GET` requests in traditional REST APIs. In this step you will create `mutation`s for creating, updating and deleting a `person`.

Add the following mutations to your schema:
```javascript
type Mutation {
    createPerson(name: String, practiceId: Int, projectIds: [Int]): Person
    updatePerson(id: Int!, name: String, practiceId: Int, projectIds: [Int]): Person
    deletePerson(id: Int!): Int
}
```

Like queries, Spring will look for a `@Component` implementing `GraphQLMutationResolver` and will expect all methods to have exactly one implementation. You may copy the implementation from the `PersonService` class.

Try adding a new `Person` (from Postman):
```javascript
{
	"query": "mutation { createPerson(name: \"Chiel\", practiceId: 1, projectIds: [1]) { id name createdAt updatedAt practice { id name } projects { id name } } }"
}
```
Notice that the json still has the `"query"` key, while the value has a `mutation` indicator.
Notice that you you can specify which fields you want returned from this operation (remember, it returns a `Person`) - moreover, you are required to specify at least one returning field!

### Step 9: Exception handling