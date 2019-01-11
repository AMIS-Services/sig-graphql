import Sequelize from "sequelize";
const sequelize = new Sequelize("postgres://postgres:postgres_password@localhost:5432/postgres", {
  define: { timestaps: false }
});

export default sequelize;
