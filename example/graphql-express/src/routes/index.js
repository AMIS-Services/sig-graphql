import express from "express";
import peopleRouter from "./people/peopleRouter";
import practicesRouter from "./practices/practicesRouter";
import projectsRouter from "./projects/projectsRouter";
const router = express.Router();

router.use("/people", peopleRouter);
router.use("/practices", practicesRouter);
router.use("/projects", projectsRouter);

export default router;
