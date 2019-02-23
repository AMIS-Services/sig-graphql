import React, { Component } from "react";
import { Router } from "react-router-dom";
import Frame from "./components/Frame";
import { ApolloProvider } from "react-apollo";
import createHistory from "history/createBrowserHistory";
import "./App.css";
import { graphqlClient } from "./common/fetch.js";

const browserHistory = createHistory();

class App extends Component {
  render() {
    return (
      <ApolloProvider client={graphqlClient}>
        <Router history={browserHistory}>
          <Frame />
        </Router>
      </ApolloProvider>
    );
  }
}

export default App;
