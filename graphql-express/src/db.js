import Sequelize from "sequelize";
import definePerson from "./routes/people/personEntity";
import definePractice from "./routes/practices/practiceEntity";

const sequelize = new Sequelize("postgres://postgres:postgres_password@localhost:5432/postgres", {
  define: { timestaps: false }
});

const db = {};

db.sequelize = sequelize;
db.person = definePerson(sequelize);
db.practice = definePractice(sequelize);

db.practice.hasMany(db.person);
db.person.belongsTo(db.practice);

export default db;
