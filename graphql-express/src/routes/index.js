import express from "express";
import peopleRouter from "./people/router";

const router = express.Router();

router.use("/people", peopleRouter);

export default router;
