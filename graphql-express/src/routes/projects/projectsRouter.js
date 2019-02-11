import express from "express";
import db from "../../db";

const Project = db.project;
const Person = db.person;
const router = express.Router();

router.get("/", async (_, res) => {
  const projects = await Project.findAll({
    include: [{ model: Person, attributes: ["id"], through: { attributes: [] } }]
  });
  res.json({ projects });
});

router.get("/:id", async (req, res) => {
  const project = await Project.findByPk(req.params.id, {
    include: [{ model: Person, attributes: ["id"], through: { attributes: [] } }]
  });
  if (!project) res.status(404).json({ message: "project not found" });
  res.json(project);
});

export default router;
