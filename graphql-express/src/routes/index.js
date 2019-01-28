import express from "express";
import peopleRouter from "./people/peopleRouter";
import practicesRouter from "./practices/practicesRouter";

const router = express.Router();

router.use("/people", peopleRouter);
router.use("/practices", practicesRouter);

export default router;
