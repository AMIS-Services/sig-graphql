import express from "express";
import db from "../../db";

const Project = db.project;
const Person = db.person;
const router = express.Router();

router.get("/", async (_, res) => {
  const projects = await Project.findAll({ include: [{ model: Person, attributes: ["id"] }] });
  res.json({ projects });
});

export default router;
