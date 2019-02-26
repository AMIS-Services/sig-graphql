import express from "express";
import db from "../../db";

const Op = db.sequelize.Op;
const Practice = db.practice;
const Person = db.person;
const Project = db.project;
const router = express.Router();

const include = [
  { model: Person, attributes: ["id"] },
  { model: Project, attributes: ["id"], through: { attributes: [] } }
];
router.get("/", async (_, res) => {
  const practices = await Practice.findAll({ include });
  res.json(practices);
});

router.get("/:id", async (req, res) => {
  const practice = await Practice.findByPk(req.params.id, { include });
  if (!practice) res.status(404).json({ message: "practice not found" });
  res.json(practice);
});

router.post("/", async (req, res) => {
  const practice = req.body;
  const savedPractice = await Practice.create({ ...practice });
  res.status(201).json(savedPractice);
});

router.patch("/:id", async (req, res) => {
  const practice = req.body;

  const [_, [updatedPractice]] = await Practice.update(practice, {
    where: { id: req.params.id },
    returning: true
  });
  if (!updatedPractice) res.status(400).json({ message: "bad request" });

  if (practice.projects) {
    const projects = await Project.findAll({ where: { id: { [Op.or]: practice.projects.map(proj => proj.id) } } });
    await updatedPractice.setProjects(projects);
  }

  if (practice.people) {
    const people = await Person.findAll({ where: { id: { [Op.or]: practice.people.map(pers => pers.id) } } });
    await updatedPractice.setPeople(people);
  }

  const practiceWithAssociations = await Practice.findByPk(req.params.id, { include });
  res.json(practiceWithAssociations);
});

export default router;
