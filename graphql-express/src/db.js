import Sequelize from "sequelize";
import definePerson from "./routes/people/personEntity";
import definePractice from "./routes/practices/practiceEntity";
import defineProject from "./routes/projects/projectEntity";

const sequelize = new Sequelize("postgres://postgres:postgres_password@localhost:5432/postgres", {
  define: { timestamps: false }
});

const db = {};

db.sequelize = sequelize;
db.person = definePerson(sequelize);
db.practice = definePractice(sequelize);
db.project = defineProject(sequelize);

db.person.belongsTo(db.practice);
db.practice.hasMany(db.person);

db.person.belongsToMany(db.project, { through: "PersonProject" });
db.project.belongsToMany(db.person, { through: "PersonProject" });

db.practice.belongsToMany(db.project, { through: "PracticeProject" });
db.project.belongsToMany(db.practice, { through: "PracticeProject" });

export default db;
