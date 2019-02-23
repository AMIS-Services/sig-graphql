import db from "../../db";
const Op = db.sequelize.Op;
const Practice = db.practice;
const Person = db.person;
const Project = db.project;

export const practicesResolver = async (_, { ids }) => {
  if (ids) {
    return Practice.findAll({ where: { id: { [Op.or]: ids } } });
  }
  return Practice.findAll();
};

export const practicesPeopleResolver = async practice => {
  const fullPractice = await Practice.findByPk(practice.id, { include: [{ model: Person }] });
  return fullPractice.people;
};

export const practicesProjectsResolver = async practice => {
  const fullPractice = await Practice.findByPk(practice.id, { include: [{ model: Project }] });
  return fullPractice.projects;
};
