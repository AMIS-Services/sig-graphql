import Sequelize from "sequelize";

const definePerson = sequelize =>
  sequelize.define(
    "person",
    {
      name: Sequelize.STRING
    },
    { tableName: "people" }
  );

export default definePerson;
