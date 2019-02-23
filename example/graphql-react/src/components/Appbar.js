import React from "react";
import { Link, withRouter } from "react-router-dom";
import "./Appbar.css";

class Appbar extends React.Component {
  render() {
    const { pathname: path } = this.props.location;
    return (
      <div className="appbar">
        <h1 className="title">AMIS</h1>
        <Link className={`nav-tab ${path === "/" || path === "/people" ? "active-route" : null}`} to="/people">
          People
        </Link>
        <Link className={`nav-tab ${path === "/practices" ? "active-route" : null}`} to="/practices">
          Practices
        </Link>
        <Link className={`nav-tab ${path === "/projects" ? "active-route" : null}`} to="/projects">
          Projects
        </Link>
      </div>
    );
  }
}

export default withRouter(Appbar);
