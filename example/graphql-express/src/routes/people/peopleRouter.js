import express from "express";
import db from "../../db";

const router = express.Router();
const Person = db.person;
const Practice = db.practice;
const Project = db.project;
const include = [
  { model: Project, attributes: ["id"], through: { attributes: [] } },
  { model: Practice, attributes: ["id"] }
];

router.get("/", async (_, res) => {
  const people = await Person.findAll({ include });
  res.json(people);
});

router.get("/:id", async (req, res) => {
  const person = await Person.findByPk(req.params.id, { include });
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
  const [_, [updatedPerson]] = await Person.update(person, { where: { id: req.params.id }, update: true });
  if (!updatedPerson) res.status(400).json({ message: "bad request" });
  res.json(updatedPerson);
});

export default router;
