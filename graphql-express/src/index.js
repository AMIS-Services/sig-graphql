import express from "express";
import router from "./routes";
import http from "http";
import bodyParser from "body-parser";
import db from "./db";

const PORT = 3030;
const ADRESS = "127.0.0.1";
const app = express();

db.sequelize.sync();

app.get("/", (_, res) => {
  res.send("server up, happy hacking");
});

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use("/api", router);

http.createServer(app).listen(PORT, ADRESS, () => {
  console.log(`server up at ${ADRESS}:${PORT}`);
});
