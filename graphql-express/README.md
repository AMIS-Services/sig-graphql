# Express + GraphQL

This is the express project for the GraphQL SIG. Express is the most popular framework for node. The project can be run in its current form by running the steps under [getting started](#getting-started) as a REST API against which the (react) front-end can perform its queries.
The project contains 3 rest endpoints which can be found at `localhost:3030/api/people`, `localhost:3030/api/practices` and `localhost:3030/api/projects`.
Going through the [hands-on](#hands-on) steps will let you get some practical experience with GraphQL.

Instead of basic GraphQL we will be using Apollo GraphQL for this tutorial. Apollo adds many useful features, such as caching and subscriptions to GraphQL. Apollo has excellent documentation which can be found at [https://www.apollographql.com/docs/](https://www.apollographql.com/docs/).

## Getting started

Before you get started,make sure you have docker installed, running and are able to run a postgres image (`docker run postgres`).

In order to run the project, execute these steps in the express root folder:

- `npm install` to install the npm packages
- `npm run start-db` to start the docker container with PostgresQL.
- In case your db cannot be reached at localhost:5432, modify `src/db.js` with the correct address.
- `npm run start`
- open a new terminal at the same path
- `npm run seed-db`

## Hands-on

### Step 1: Hello world

After you get the project running, we can get started on the transformation to GraphQL by taking care of four things:

- create type definitions
- create necessary resolvers
- create express middleware based on the schema
- add the middleware to our existing express app

We will start of by adding some type definitions. In order to do this, import `gql` from the node package 'apollo-server-express'. This gql tag will allow you to write type definitions inside backticks while still having syntax highlighting if you install the GraphQL extension for VS Code.

Open the file `src/index.js` and initialize a new constant called `typeDefs` and assign the following value to it:

```javascript
const typeDefs = gql`
  type Query {
    hello: String
  }
`;
```

Time to create the resolver which will power our hello query. Add them as follows:

```javascript
const resolvers = {
    Query: {
        hello: () => 'Hello world!
    }
}
```

Now that we have our type definitions and resolvers we can create our middleware. Import `ApolloServer` from 'apollo-server-express' and add the following code block.

```javascript
const schema = new ApolloServer({
  typeDefs,
  resolvers
});
```

Now all that remains is adding our Apollo GraphQL middleware to the rest of our server!

```javascript
schema.applyMiddleware({ app, path: "/api/graphql" });
```

Go ahead and visit [localhost:3030/api/graphql](localhost:3030/api/grapqhl). Try adding a query for hello.

```
query {
    hello
}
```

There you go, you now know how to use GraphQL! Kinda.

### Step 2: Expanding the schema

Let's start working towards making the schema more practical for our actual purpose: replacing our REST api. However, in order to keep things nice and clean, let's first move all the GraphQL related content to its own file. Create the file `src/schema.js` and move the typeDefs, resolvers and schema creatoin there. Don't forget to move your imports!

Now that we've cleaned that up, we can continue working. Since GraphlQL is typed, we have to set up typings for all our queries. Add a type to the typeDefs for Person. This Person type should have a name, which is a `String` and an id, which is an `Int`. In the Query type, remove `hello` and add `people`. People is of the type `[Person]`, an array of `Person`.

When we query `people` GraphQL won't know where to find them, since we have not yet added a resolver. Let's remove `hello` from the resolvers and add `people`. For now, just let it return a static array: `[{id: 1, name: 'henk'}]`. Go to [localhost:3030/api/graphql](localhost:3030/api/grapqhl) and check if everything is working as expected.

### Step 3: Wrapping a REST endpoint

Returning static data is nice and all, but what we actually want is some real data. A nice and quick way to get GraphQL up and running when you already have a REST endpoint is to simply wrap it. Since we will be expanding our `People` resolver, lets move it to its own file. Create `src/routes/people/peopleResolver.js` and export the function `peopleResolver` from there. Import this function in `src/schema.js` and use it in the root resolver.

In the new resolver file you've just created, import fetch from 'node-fetch'. This will allow us to much more easily perform http request than with the old JS standard of XMLHttpRequest. At the top of the file, define the constant baseUrl with the value `"http://localhost:3030/api"`. In the function body, replace the return of the static array with the following code:

```javascript
return fetch(baseUrl + "/people").then(res => res.json());
```

Go to [localhost:3030/api/graphql](localhost:3030/api/grapqhl) and check it out. Isn't that easy?

### Step 4: Inputs

Now that know how return all the people from our GraphQL API, it would be nice to be able to return just one specific person. In order to do so, we will have to create an input on our query. You can add a parameter `id` with the type of `Int` to the people query under the type Query. It should look like this:

```javascript
  type Query {
    people(id: Int): [Person]
  }
```

If you try it out at [localhost:3030/api/graphql](localhost:3030/api/grapqhl) you will notice that this input does not actually do anything yet. That's because we haven't implemented it in our resolver, of course. Go to your `peopleResolver` and change the parameters to the following structure: `(_, { id })`. The underscore means that we will not use the first parameter, which is an object which contains the results returned from the resolver on the parent field. Check out [this page](https://www.apollographql.com/docs/graphql-tools/resolvers.html) for more information. In the second parameter, we use ES6 [object destructuring](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment#Object_destructuring) to get the id. Update the function body to check if an `id` was passed and in case it was, to return an array with the object for the person who's id was passed. Check if your method works on the graphql endpoint in your browser.

### Step 5: Writing a new resolver

While writing wrappers around REST endpoints is a nice way to get quickly get a lot of functionality back when moving from REST to GraphQL, true power comes when writing new resolvers. Set up a `practicesResolver` like you did the people resolver, by adding practices to your schema and creating a file at `src/routes/practices/practicesResolver.js`. Don't add an input to the typeDefs just yet.

In the resolver import `db` from `'../../db'` and add `const Practice = db.practice`. This allows us to quickly do calls on our PostgresQL database through [sequelize, an ORM](http://docs.sequelizejs.com/). In the body of your practicesResolver, all you need to do is `return Practice.findAll()`. It's that easy! Go check if it works in your browser.

We want to be able to look for specific practices on this resolver as well, so let's add an input. This time, add the input `(ids: [Int])`, so that we can look up either one or more ids at the same time. In your resolver, add `const Op = db.sequelize.Op;`. To the function body of your resolver, add code which checks if any ids have been passed and if that is the case, return `Practice.findAll({ where: { id: { [Op.or]: ids } } });`.

### Step 6: Nested resolvers

One of the nicest features of GraphQL is the ability to query nested resolvers. These resolvers can return data on associated objects, but will only be called when the query needs them to be called. So if the data from the nested resolvers is not needed, no time will be wasted on resolving it.

### Step 7: Front-end intermezzo

### Step 8: Mutations

- caching
- subscriptions
- extra external rest endpoint
