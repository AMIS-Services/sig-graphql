import React from "react";
import { Query } from "react-apollo";
import gql from "graphql-tag";

export default class Practices extends React.Component {
  render() {
    return (
      <Query query={query} variables={{ ids: [1] }}>
        {({ loading, error, data }) => {
          if (loading) return <p>loading...</p>;
          if (error) return null;
          return (
            <div className="card-container">
              {data.practices.map(practice => (
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
        }}
      </Query>
    );
  }
}

const query = gql`
  query practices($ids: [Int]) {
    practices(ids: $ids) {
      name
      id
      projects {
        name
        id
      }
      people {
        name
        id
      }
    }
  }
`;
