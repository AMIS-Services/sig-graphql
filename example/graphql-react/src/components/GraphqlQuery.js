import React from "react";
import { Query } from "react-apollo";

export default class GraphlQuery extends React.Component {
  render() {
    return (
      <Query {...this.props}>
        {({ loading, error, data }) => {
          if (loading) return <p>loading...</p>;
          if (error) {
            alert(error.message);
            return null;
          }
          return this.props.children(data);
        }}
      </Query>
    );
  }
}
