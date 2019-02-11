import React from "react";
import { Switch, Route } from "react-router-dom";
import People from "./People";
import Appbar from "./Appbar";
import Projects from "./Projects";
import Practices from "./Practices";
import "./Frame.css";

class Frame extends React.Component {
  render() {
    return (
      <div className="root">
        <Appbar />
        <Switch>
          <Route exact path="/" component={People} />
          <Route path="/practices" component={Practices} />
          <Route path="/projects" component={Projects} />
        </Switch>
      </div>
    );
  }
}

export default Frame;
