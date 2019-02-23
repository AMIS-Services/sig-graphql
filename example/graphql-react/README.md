# React + GraphQL

This is the react project for the GraphQL SIG. Express is the most popular front-end framework. The project can be ran in its current form by executing the steps under [getting started](#getting-started) as a website which consumes an API provided by either the express or the spring project. The website has 3 paths, `/people`, `/practices` and `/projects`, corresponding to the 3 REST endpoints.
Going through the [hands-on](#hands-on) steps will let you get some practical experience with GraphQL in the front-end.

Within this project, we will be using the Apollo client for GraphQL, since this offers some advantages over standard GraphQL such as caching and subsriptions. You can find the documentation for the Apollo client at [https://www.apollographql.com/docs/react/](https://www.apollographql.com/docs/react/).

## Getting Started

This project requires node (8+).

In order to run the project, execute these steps in the react root folder

- `npm install`
- `npm start`

## Hands-on

### Step 1: Adding the GraphQL client & provider

In order to get going, start up the project by following the steps under [getting started](#getting-started). This will launch the website in your browser. The website will auto-refresh every time you make a change, so there will be no need to restart the server on every change.

Before we can fetch data through GraphQL we have to do a small amount of setup. We need to configure our GraphQL client and provide it to the rest of our app, so that we can use it wherever we like. Open the file `src/common/fetch.js` and add the following code:

```javascript
import { ApolloClient } from "apollo-client";
import { HttpLink } from "apollo-link-http";
import { InMemoryCache } from "apollo-cache-inmemory";

export const graphqlClient = new ApolloClient({
  link: new HttpLink({ uri: "http://localhost:3030/api/graphql" }),
  cache: new InMemoryCache()
});
```

Now add `src/App.js` and provide the client to the nested components:

```javascript
import { ApolloProvider } from "react-apollo";
import { graphqlClient } from "./common/fetch.js";
// code
<ApolloProvider client={graphqlClient}>
  <Router history={browserHistory}>
    <Frame />
  </Router>
</ApolloProvider>;
// code
```

Great! That's all we had to do! Let's start pulling data from our GraphQL API.

### Step 2: Implementing the query component

Open up the file `src/components/Practices.js` and remove the `componentDidMount` lifecycle method. We won't be needing that messy jumble anymore! You can also remove the state initialization line `state = {practices: []`.

Lets start of by writing our query. Import the default export `glq` from "graphql-tag" and implement it below your component:

```javascript
const query = gql`
  query practices {
    practices {
      id
      name
      projects {
        id
        name
      }
      people {
        id
        name
      }
    }
  }
`;
```

This query will grab all the data from the practices resolver and the nested resolvers which we just implemented in the backend. Now we need some code to execute this query.

Import the `Query` component from "react-apollo" and rewrite the render function to implement it. The Query component is a renderProps component, which means that it takes a function as child in the following style:

```javascript
<Query query={query}>
  {({ loading, error, data }) => {
    return <div>JSX goes here</div>;
  }}
</Query>
```

Decide on what you want to do with the loading and error parameters. `console.log()` the data to see what the data structure is like and rewrite the JSX away from the `this.state` based data management.

### Step 3: Inputs and variables

Now that we are consuming our GraphQL API, we want some more freedom than just selecting which fields we want to fetch. We want to be able to use our inputs! Lets get to work on adding our `ids` input which we implemented in our backend. We can add the inputs just as we did when trying them out on the webpage provided by the webpage, for example `practices(ids: 1)`, but that does not give us a lot of flexibility, does it?. What we really want is to be able to use variables.
All we need to do is change our query a little bit:

```javascript
query practices($ids: [Int]) {
    practices(ids: $ids) {
        // the rest of the query
    }
}
```

Apollo now nows that we expect a variables objec to be passed, with a key of `ids` and a corresponding value of an array of integers. So lets implement that in our Query component.

```javascript
<Query query={query} variables={{ ids: [1] }}>
  {({ loading, error, data }) => {
    // JSX
  }}
</Query>
```

Apollo will figure out the rest for you. Isn't that nice and easy? Don't forget to take a look at your page to see if everything is still functioning as expected.

### Step 4: Creating a custom query component

While having the flexibility of implementing different load and error options everywhere is nice, it is not always needed. Sometimes you just want to handle your errors in the same way everywhere. We can just create a new component which handles the loading and errors and returns the data. Create a new file `src/components/GraphqlQuery.js` and set up a new component. In the components render function, implement the `Query` component you used in the Practices component. Pass any props you might have to the Query component (hint: use the ES6 [spread syntax](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Spread_syntax)). Handle loading state by returning a placeholder. Handle errors by showing an alert. This component will have a function as a child, just like the Query component did, so you can pass the data to the function with `return this.props.children(data)`. When you are done writing your new component, replace the Query component in `src/components/Practices.js` with your new GraphqlQuery component. Check out your website to see if everything still works.

### Step 5: Return to the back-end

Alright, that was it for the front-end for now. Return to where you left off in your back-end project and continue with the next step.
