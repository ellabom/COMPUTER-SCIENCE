//emmanuella eyo 11291003 eee917
"use strict";

const express = require("express");
const app = express();

const bodyParser = require("body-parser");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const PORT = 8080;
const HOST = "0.0.0.0";

const path = __dirname;

const nano = require("nano")("http://admin:admin@couchdb1:5984");
const posts = nano.use("posts");

//get all posts
app.get("/posts", (req, res) => {
  posts.list({ include_docs: true }, (err, body) => {
    if (err) {
      res.status(500).json({ error: err });
    } else {
      const response = body.rows.map((row) => ({
        id: row.doc._id,
        topic: row.doc.topic,
        data: row.doc.data,
        getComments: { verb: "get", route: `/posts/${row.doc._id}/comments` },
        addComment: {
          verb: "post",
          route: `/posts/${row.doc._id}/comments`,
          args: ["commentText"],
        },
      }));

      res.json({ posts: response });
    }
  });
});

//get posts with id
app.get("/posts/:id", (req, res) => {
  posts.get(req.params.id, { include_docs: true }, (err, body) => {
    if (err) {
      res.status(404).json({ error: "post not found" });
    } else {
      const response = {
        id: body._id,
        topic: body.topic,
        data: body.data,
        comments: { verb: "get", route: `/posts/${body._id}/comments` },
        addComment: {
          verb: "post",
          route: `/posts/${body._id}/comments`,
          desc: "Add comments for this post",
          args: ["commentText"],
        },
      };
      res.json(response);
    }
  });
});

app.post("/posts", (req, res) => {
  const { id, topic, data } = req.body;

  if (!id || !topic || !data) {
    res.status(400).json({ error: "Id, topic, and data are required" });
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

app.get("/posts/:id/comments", (req, res) => {
  posts.get(req.params.id, { include_docs: true }, (err, body) => {
    if (err) {
      res.status(404).json({ error: "Post not found" });
    } else {
      const comments = body.comments || [];
      const response = {
        comment: comments,
        posts: { route: `/posts/${body._id}`, desc: "See comment's post" },
        addComment: {
          verb: "POST",
          route: `/posts/${body._id}/comments`,
          desc: "Add comment",
          args: ["commentText"],
        },
      };
      res.json(response);
    }
  });
});

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
      console.log("Before:", post);

      const comment = { id: post.comments.length + 1, text: commentText };
      post.comments.push(comment);
      console.log("After:", post);

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

app.delete("/posts/:id", (req, res) => {
  posts.get(req.params.id, (err, post) => {
    if (err) {
      res.status(500).json({ error: err.message });
    } else if (!post) {
      res.status(404).json({ error: "Post not found" });
    } else {
      posts.destroy(post._id, post._rev, (err, result) => {
        if (err) {
          res.status(500).json({ error: err.message });
        } else {
          res.json({ message: "Post deleted successfully" });
        }
      });
    }
  });
});

app.get("/", (req, res) => {
  res.sendFile("TestRestLevel3.html", { root: path + "/" });
});

app.listen(8080, () => {
  nano.db.create("posts", (err, body) => {
    if (!err) {
      console.log("Database created!");
    } else {
      console.log("Database exists already");
    }
  });
  console.log(`Running on http://${HOST}:${PORT}`);
});
