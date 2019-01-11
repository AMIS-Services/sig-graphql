import express from "express";
import Person from "./entity";

const router = express.Router();

router.get("/", async (_, res) => {
  const people = await Person.findAll();
  res.json({ people });
});

router.post("/", async (req, res) => {
  const { person } = req.body;
  const createdPerson = await Person.create({ ...person });
  res.status(201).json(createdPerson);
});

export default router;
