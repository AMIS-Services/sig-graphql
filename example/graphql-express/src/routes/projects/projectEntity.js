import Sequelize from "sequelize";

const defineProject = sequelize => sequelize.define("project", { name: Sequelize.STRING }, { tableName: "projects" });

export default defineProject;
