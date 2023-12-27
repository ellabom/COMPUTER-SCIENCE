//EMMANUELLA EY0 EEE917 11291003

import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./ShowPosts.css";
function ShowPosts() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:3080/getPost")
      .then((res) => res.json())
      .then((data) => setPosts(data));
  }, []);

  return (
    <div>
      <table>
        <thead>
          <tr>
            <th>Topic</th>
            <th>Data</th>
          </tr>
        </thead>
        <tbody>
          {posts.map((post) => (
            <tr key={post.ID}>
              <td>{post.Topic}</td>
              <td>{post.Data}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <Link to="/">
        <button>Back</button>
      </Link>
    </div>
  );
}

export default ShowPosts;
