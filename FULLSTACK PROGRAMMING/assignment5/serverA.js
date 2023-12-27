//EMMANUELLA EYO EEE917 11291003
"use strict";

// load packages
const express = require("express");
const app = express();

const bodyParser = require("body-parser");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const path = __dirname;
const PORT = 8080;
const HOST = "0.0.0.0";

var nano = require("nano")("http://admin:admin@couchdb1:5984");
var posts = nano.use("posts");

// list all posts
app.get("/posts", (req, res) => {
  posts.list({ include_docs: true }, (err, body) => {
    if (err) {
      res.status(500).json({ error: err });
    } else {
      res.json(body.rows.map((row) => row.doc));
    }
  });
});

// get a post by id
app.get("/posts/:id", (req, res) => {
  posts.get(req.params.id, { include_docs: true }, (err, body) => {
    if (err) {
      res.status(404).json({ error: "Post not found" });
    } else {
      res.json(body);
    }
  });
});

// create a new post
app.post("/posts", (req, res) => {
  const { id, topic, data } = req.body;

  posts.list({ include_docs: true }, (err, body) => {
    if (err) {
      res.status(500).json({ error: err });
    } else {
      posts.insert({ _id: id, topic, data }, function (err, body, header) {
        if (err) {
          res.status(500).json({ error: err });
        } else {
          res.json(body);
        }
      });
    }
  });
});

// list all comments for a post
app.get("/posts/:id/comments", (req, res) => {
  posts.get(req.params.id, { include_docs: true }, (err, body) => {
    if (err) {
      res.status(404).json({ error: "Post not found" });
    } else {
      const comments = body.comments || [];
      res.json(comments);
    }
  });
});

// create a new comment for a post
app.post("/posts/:id/comments", (req, res) => {
  const { commentText } = req.body;
  const postId = req.params.id;

  posts.get(postId, (err, post) => {
    if (err) {
      res.status(500).json({ error: err.message });
    } else if (!post) {
      res.status(404).json({ error: "Post not found" });
    } else {
      // Initialize post.comments to an empty array if it is undefined
      post.comments = post.comments || [];

      const comment = { id: post.comments.length + 1, text: commentText };
      post.comments.push(comment);

      posts.insert(post, (err, result) => {
        if (err) {
          res.status(500).json({ error: err.message });
        } else {
          res.json(comment);
        }
      });
    }
  });
});

app.get("/", (req, res) => {
  res.sendFile("TestRestLevel2.html", { root: path + "/" });
});
// start server
app.listen(8080, () => {
  nano.db.create("posts", function (err, body) {
    if (!err) {
      console.log("Database created!");
    } else {
      console.log("Database exists already");
    }
  });
  console.log(`Running on http://${HOST}:${PORT}`);
});
