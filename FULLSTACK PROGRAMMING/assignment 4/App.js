//EMMANUELLA EY0 EEE917 11291003

import React, { useEffect } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link,
  useLocation,
} from "react-router-dom";
import AddPost from "./AddPosts";
import ShowPosts from "./ShowPosts";
import "./App.css";

function App() {
  useEffect(() => {
    initDatabase();
  }, []);

  function initDatabase() {
    fetch("http://localhost:3080/database", {
      method: "POST",
    })
      .then((response) => response.text())
      .then((data) => console.log(data))
      .catch((error) => console.error(error));
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/add" element={<AddPost />} />
        <Route path="/show" element={<ShowPosts />} />
      </Routes>
      <Nav />
    </Router>
  );
}

function Landing() {
  return <h1>Post Page</h1>;
}

function Nav() {
  const location = useLocation();

  if (location.pathname === "/add" || location.pathname === "/show") {
    return null; // hide buttons on /add and /show
  }

  return (
    <nav>
      <div className="container">
        <ul>
          <li>
            <Link to="/add">
              <button className="center"> Add Post </button>
            </Link>
          </li>
          <li>
            <Link to="/show">
              <button className="center"> Show Posts </button>
            </Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}

export default App;
