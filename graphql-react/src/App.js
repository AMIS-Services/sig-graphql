import React, { Component } from "react";
import { Router } from "react-router-dom";
import Frame from "./components/Frame";
import createHistory from "history/createBrowserHistory";
import "./App.css";

const browserHistory = createHistory();

class App extends Component {
  render() {
    return (
      <Router history={browserHistory}>
        <Frame />
      </Router>
    );
  }
}

export default App;
