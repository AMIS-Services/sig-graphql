import Sequelize from "sequelize";

const definePractice = sequelize =>
  sequelize.define(
    "practice",
    {
      name: Sequelize.STRING
    },
    { tableName: "practices" }
  );

export default definePractice;
