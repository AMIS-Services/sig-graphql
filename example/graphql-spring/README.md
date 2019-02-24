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
    <version>5.2.4</version>
</dependency>
<!--GraphQL-Java Spring boot starter for GraphQL - hosts our schema at the /graphql endpoint in our spring context-->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphql-spring-boot-starter</artifactId>
    <version>5.0.2</version>
</dependency>
<!--GraphQL-Java Spring boot starter for GraphIQL - a web based UI for interacting with the /graphql endpoint, with knowledge of the schema at the endpoint-->
<dependency>
    <groupId>com.graphql-java</groupId>
    <artifactId>graphiql-spring-boot-starter</artifactId>
    <version>5.0.2</version>
</dependency>
```

The application is configured to search for graphql schema files on the classpath with extension `.graphqls`. Let's add a simple schema file with the following content:

```text
type Query {
    hello: String
}
```

This schema defines the `hello` query, which returns a String.

The application is configured to search for graphql resolvers on the classpath, java classes that implement `GraphQLQueryResolver` and are annotated with `@Controller`. You may have multiple java classes like this, but the queries defined in the graphql schema must and may only exist once among those java classes. 
Create a new java class that implements `GraphQLQueryResolver`, is annotated with `@Controller` and has a public method called `hello()` returning a String - for instance "Hello world!".

Now run the application and try the hello-query.
In Postman you could do the following POST to `http://localhost:3030/graphql`:
```javascript
{
	"query": "{ hello }"
}
``` 
Alternatively use graphiQL in the browser at `http://localhost:3030/graphiql`. This is a web based UI for interacting with the /graphql endpoint. Note that it is (over)simplified; the queries are not valid json. Use for example:
```javascript
{
	hello
}
``` 

### Step 2: Expanding the schema

### Step 3: Wrapping a REST endpoint

### Step 4: Inputs

### Step 5: Writing a new resolver

### Step 6: Nested resolvers

### Step 7: To the front-end!

### Step 8: Mutations

### Step 9: Exception handling