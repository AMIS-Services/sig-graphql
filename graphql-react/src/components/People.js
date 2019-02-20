import React from "react";
import { fetch } from "../common/fetch";

class People extends React.Component {
  state = { people: [] };

  async componentDidMount() {
    const people = await fetch("/people");
    const enrichedPeople = await Promise.all(
      people.map(async person => {
        const projects = await Promise.all(
          person.projects.map(async project => await fetch(`/projects/${project.id}`))
        );
        person.projects = projects;

        const practice = await fetch(`/practices/${person.practice.id}`);
        person.practice = practice;
        return person;
      })
    );
    this.setState({ people: enrichedPeople });
  }

  render() {
    return (
      <div className="card-container">
        {this.state.people.map(person => (
          <div key={person.id} className="card">
            <div className="card-header">
              <span>{person.name}</span>
            </div>
            <div className="card-contents">
              <div className="card-section">
                <span className="card-subheader">practice</span>
                <div>{person.practice.name}</div>
              </div>
              {person.projects.length > 0 && (
                <div className="card-section">
                  <span className="card-subheader">projects</span>
                  {person.projects.map(project => (
                    <div key={`project${project.id}`}>{project.name}</div>
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

export default People;
