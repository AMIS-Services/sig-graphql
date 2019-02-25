import { ApolloServer, gql } from "apollo-server-express";
import peopleResolver from "./routes/people/peopleResolver";
import {
  practicesResolver,
  practicesPeopleResolver,
  practicesProjectsResolver,
  updatePracticeResolver
} from "./routes/practices/practicesResolver";
import { projectsResolver } from "./routes/projects/projectsResolver";

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

  input PracticeInput {
    id: Int!
    name: String
  }

  type Project {
    id: Int
    name: String
  }

  type Query {
    people(id: Int): [Person]
    practices(ids: [Int]): [Practice]
    projects(name: String): [Project]
  }

  type Mutation {
    updatePractice(practice: PracticeInput!): Practice
  }
`;

const resolvers = {
  Query: {
    people: peopleResolver,
    practices: practicesResolver,
    projects: projectsResolver
  },
  Mutation: {
    updatePractice: updatePracticeResolver
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
