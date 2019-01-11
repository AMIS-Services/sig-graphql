import Sequelize from "sequelize";
import db from "../../db";

const Person = db.define(
  "person",
  {
    name: Sequelize.STRING
  },
  { tableName: "people" }
);

Person.sync();

export default Person;
