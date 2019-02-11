import React from "react";
import { Switch, Route } from "react-router-dom";
import People from "./People";
import Appbar from "./Appbar";
import "./Frame.css";

class Frame extends React.Component {
  render() {
    return (
      <div className="root">
        <Appbar />
        <Switch>
          <Route path="/" component={People} />
        </Switch>
      </div>
    );
  }
}

export default Frame;
