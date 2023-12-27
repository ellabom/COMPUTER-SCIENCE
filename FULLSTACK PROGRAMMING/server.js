"use strict";

// load package
const express = require("express");
const bodyParser = require("body-parser");
const fs = require("fs");

const PORT = 8080;
const HOST = "0.0.0.0";
const app = express();

app.use(bodyParser.urlencoded({ extended: true }));

app.post("/postmessage", (req, res) => {
  const topic = req.body.topic;
  const data = req.body.data;
  const timestamp = new Date();

  fs.appendFile("posts.txt", "${topic}, ${data}, ${timestamp}\n", (err) => {
    if (err) {
      console.log(err);
    }
  });
});

app.listen(PORT, HOST);
console.log("up and running");
