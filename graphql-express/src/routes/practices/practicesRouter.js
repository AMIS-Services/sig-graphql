import express from "express";
import db from "../../db";

const Practice = db.practice;
const router = express.Router();

router.get("/", async (_, res) => {
  const practices = await Practice.findAll();
  res.json({ practices });
});

router.get("/:id", async (req, res) => {
  const practice = await Practice.findByPk(req.params.id);
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

  const affectedLines = await Practice.update(practice, { where: { id: req.params.id } });
  if (affectedLines[0] !== 1) res.status(400).json({ message: "bad request" });

  const savedPractice = await Practice.findById(req.params.id);
  res.json(savedPractice);
});

export default router;
