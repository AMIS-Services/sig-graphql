import { UserInputError } from "apollo-server-express";
import db from "../../db";
const Project = db.project;

export const projectsResolver = (_, { name }) => {
  if (name) {
    if (/[^\a-zA-Z \-]/.test(name)) throw new UserInputError("name contains illegal characters");
    return Project.findAll({ where: { name } });
  }
  return Project.findAll();
};
