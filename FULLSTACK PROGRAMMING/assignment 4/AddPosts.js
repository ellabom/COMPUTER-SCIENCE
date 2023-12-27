//EMMANUELLA EY0 EEE917 11291003

import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./AddPosts.css";

function AddPost() {
  const [topic, setTopic] = useState("");
  const [data, setData] = useState("");

  const handleSubmit = (event) => {
    event.preventDefault();
    fetch("http://localhost:3080/addPost", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ topic, data }),
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);
        setTopic("");
        setData("");
        window.alert("Post added successfully!");
      })
      .catch((error) => console.error(error));
  };

  return (
    <div className="container">
      <h1>Create Post</h1>
      <div>
        <form onSubmit={handleSubmit}>
          <label>
            Topic:
            <input
              type="text"
              value={topic}
              onChange={(e) => setTopic(e.target.value)}
            />
          </label>
          <label>
            Data:
            <input
              type="text"
              value={data}
              onChange={(e) => setData(e.target.value)}
            />
          </label>
          <br></br>
          <div className="button-container">
            <button type="submit">Add Post</button>
          </div>
        </form>
      </div>

      <br></br>
      <Link to="/">
        <button>Back</button>
      </Link>
    </div>
  );
}

export default AddPost;
