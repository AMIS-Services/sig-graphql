import express from "express";
import db from "../../db";

const Person = db.person;
const Project = db.project;
const router = express.Router();

router.get("/", async (_, res) => {
  const people = await Person.findAll({
    include: [{ model: Project, attributes: ["id"], through: { attributes: [] } }]
  });
  res.json(people);
});

router.get("/:id", async (req, res) => {
  const person = await Person.findByPk(req.params.id, {
    include: [{ model: Project, attributes: ["id"], through: { attributes: [] } }]
  });
  if (!person) res.status(404).json({ message: "person not found" });
  res.json(person);
});

router.post("/", async (req, res) => {
  const person = req.body;
  const savedPerson = await Person.create({ ...person });
  res.status(201).json(savedPerson);
});

router.patch("/:id", async (req, res) => {
  const person = req.body;

  const affectedLines = await Person.update(person, { where: { id: req.params.id } });
  if (affectedLines[0] !== 1) res.status(400).json({ message: "bad request" });

  const savedPerson = await Person.findByPk(req.params.id);
  res.json(savedPerson);
});

export default router;
