import React from "react";

export default class Practices extends React.Component {
  state = { practices: [] };

  async componentDidMount() {
    const practices = await fetch("/practices");
    const enrichedPractices = await Promise.all(
      practices.map(async practice => {
        const projects = await Promise.all(
          practice.projects.map(async project => await fetch(`/projects/${project.id}`))
        );
        practice.projects = projects;

        const people = await Promise.all(practice.people.map(async person => await fetch(`/people/${person.id}`)));
        practice.people = people;

        return practice;
      })
    );
    this.setState({ practices: enrichedPractices });
  }

  render() {
    return (
      <div className="card-container">
        {this.state.practices.map(practice => (
          <div key={practice.id} className="card">
            <div className="card-header">{practice.name}</div>
            <div className="card-contents">
              <div className="card-section">
                <div className="card-subheader">members</div>
                {practice.people.map(person => (
                  <div key={`person${person.id}`}>{person.name}</div>
                ))}
              </div>
              {practice.projects.length > 0 && (
                <div className="card-section">
                  <div className="card-subheader">projects</div>
                  {practice.projects.map(project => (
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
