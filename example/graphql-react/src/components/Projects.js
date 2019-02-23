import React from "react";
import { fetch } from "../common/fetch";

export default class Projects extends React.Component {
  state = { projects: [] };

  async componentDidMount() {
    const projects = await fetch("/projects");
    const enrichedProjects = await Promise.all(
      projects.map(async project => {
        const people = await Promise.all(project.people.map(async person => await fetch(`/people/${person.id}`)));
        project.people = people;

        const practices = await Promise.all(
          project.practices.map(async practice => await fetch(`/practices/${practice.id}`))
        );
        project.practices = practices;

        return project;
      })
    );

    this.setState({ projects: enrichedProjects });
  }

  render() {
    return (
      <div className="card-container">
        {this.state.projects.map(project => (
          <div key={project.id} className="card">
            <div className="card-header">{project.name}</div>
            <div className="card-contents">
              <div className="card-section">
                <div className="card-subheader">members</div>
                {project.people.map(person => (
                  <div key={`person${person.id}`}>{person.name}</div>
                ))}
              </div>
              {project.practices.length > 0 && (
                <div className="card-section">
                  <div className="card-subheader">practices</div>
                  {project.practices.map(practice => (
                    <div key={`practice${practice.id}`}>{practice.name}</div>
                  ))}
                </div>
              )}
            </div>
          </div>
        ))}
      </div>
    );
  }
}
