import { ApolloServer, gql } from "apollo-server-express";
import peopleResolver from "./routes/people/peopleResolver";
import {
  practicesResolver,
  practicesPeopleResolver,
  practicesProjectsResolver
} from "./routes/practices/practicesResolver";

const typeDefs = gql`
  type Person {
    id: Int
    name: String
    practices: [Practice]
  }

  type Practice {
    id: Int
    name: String
    people: [Person]
    projects: [Project]
  }

  type Project {
    id: Int
    name: String
  }

  type Query {
    people(id: Int): [Person]
    practices(ids: [Int]): [Practice]
  }
`;

const resolvers = {
  Query: {
    people: peopleResolver,
    practices: practicesResolver
  },
  Practice: {
    people: practicesPeopleResolver,
    projects: practicesProjectsResolver
  },
  Person: {
    practices: person => []
  }
};

const schema = new ApolloServer({
  typeDefs,
  resolvers
});

export default schema;
