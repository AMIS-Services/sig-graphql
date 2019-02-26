import express from "express";
import db from "../../db";

const Practice = db.practice;
const Project = db.project;
const Person = db.person;
const router = express.Router();

const include = [
  { model: Person, attributes: ["id"], through: { attributes: [] } },
  { model: Practice, attributes: ["id"], through: { attributes: [] } }
];
router.get("/", async (_, res) => {
  const projects = await Project.findAll({ include });
  res.json(projects);
});

router.get("/:id", async (req, res) => {
  const project = await Project.findByPk(req.params.id, { include });
  if (!project) res.status(404).json({ message: "project not found" });
  res.json(project);
});

router.patch("/:id", async (req, res) => {
  const project = req.body;

  const affectedLines = await Project.update(project, {
    where: { id: req.params.id },
    include: [{ model: Person }, { model: Project }]
  });
  if (affectedLines[0] !== 1) res.status(400).json({ message: "bad request" });

  const savedProject = await Project.findByPk(req.params.id, { include });
  res.json(savedProject);
});

export default router;
