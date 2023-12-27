//EMMANUELLA EY0 EEE917 11291003
//load packages

const express = require("express");
const app = express();
const mysql = require("mysql");
const bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

const cors = require("cors");
app.use(cors());

const PORT = 3080;
const HOST = "0.0.0.0";

app.post("/database", (req, res) => {
  console.log("Database route");
  // Create a connection to the database
  const connection = mysql.createConnection({
    host: "mysql1",
    user: "root",
    password: "admin",
  });

  connection.connect((err) => {
    if (err) {
      console.error("Error connecting to database", err);
      res.sendStatus(500);
      return;
    }

    //console.log("Connected to database");

    const checkDatabaseQuery = "SHOW DATABASES LIKE 'postdb'";
    connection.query(checkDatabaseQuery, (err, results) => {
      if (err) {
        console.error("Error checking for database", err);
        res.sendStatus(500);
        return;
      }

      if (results.length === 0) {
        // The database doesn't exist, create it
        const createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS postdb";
        connection.query(createDatabaseQuery, (err, result) => {
          if (err) {
            console.error("Error creating database", err);
            res.sendStatus(500);
            return;
          }

          console.log("Database created");

          const useDatabaseQuery = "USE postdb";
          connection.query(useDatabaseQuery, (err, result) => {
            if (err) {
              console.error("Error using database", err);
              res.sendStatus(500);
              return;
            }

            const createTableQuery =
              "CREATE TABLE IF NOT EXISTS posts (ID INT AUTO_INCREMENT PRIMARY KEY, Topic VARCHAR(255), Data VARCHAR(2000))";
            connection.query(createTableQuery, (err, result) => {
              if (err) {
                console.error("Error creating table", err);
                res.sendStatus(500);
                return;
              }

              console.log("Table created");

              connection.end();

              res.sendStatus(200);
            });
          });
        });
      } else {
        const useDatabaseQuery = "USE postdb";
        connection.query(useDatabaseQuery, (err, result) => {
          if (err) {
            console.error("Error using database", err);
            res.sendStatus(500);
            return;
          }
          // The database already exists, use it
          console.log("Database already exists");
          // Delete all existing entries in the 'posts' table
          const deleteQuery = "DELETE FROM posts";
          connection.query(deleteQuery, (err, result) => {
            if (err) {
              console.error("Error deleting entries from table", err);
              res.sendStatus(500);
              return;
            }

            console.log("All entries deleted");

            // Close the database connection
            connection.end();

            res.sendStatus(200);
          });
        });
      }
    });
  });
});

// Route that handles POST requests to add new posts
app.post("/addPost", (req, res) => {
  const connection = mysql.createConnection({
    host: "mysql1",
    user: "root",
    password: "admin",
    database: "postdb",
  });
  const { topic, data } = req.body;

  // Insert new post into the "posts" table
  const sql = `INSERT INTO posts (Topic, Data) VALUES (?, ?)`;
  connection.query(sql, [topic, data], (err, result) => {
    if (err) throw err;
    console.log(`New post added: topic - ${topic} : data - ${data}`);

    // Return a success message
    res.status(201).send(`New post added: topic - ${topic} : data - ${data}`);
  });
});

// Route that handles GET requests to retrieve all existing posts
app.get("/getPost", (req, res) => {
  const connection = mysql.createConnection({
    host: "mysql1",
    user: "root",
    password: "admin",
    database: "postdb",
  });
  // Retrieve all posts from the "posts" table
  const sql = `SELECT * FROM posts`;
  connection.query(sql, (err, result) => {
    if (err) throw err;

    // Return the posts as a JSON array
    res.status(200).json(result);
  });
});

app.get("/", (req, res) => {
  res.send("API is working");
});

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);
