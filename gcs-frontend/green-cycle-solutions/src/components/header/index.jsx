import React from "react";
import LogoImage from "../../assets/images/gcs_logo.png";
import { Link } from "react-router-dom";
import "../header/style.css";

function Header() {
  return (
    <nav className="navbar navbar-expand-lg bg-body-header">
      <div className="container-fluid ">
        <img src={LogoImage} alt="GCS" width="4%" height="4%" />
        <div className="navbar-brand fs-4 ms-4" href="#">
          Green Cycle Solutions
        </div>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNavDropdown"
          aria-controls="navbarNavDropdown"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div
          className="collapse navbar-collapse flex-row-reverse"
          id="navbarNavDropdown"
        >
          <ul className="navbar-nav grid gap-4 fs-5">
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                View
              </a>
              <ul className="dropdown-menu">
                <li>
                  <Link to="/" className="dropdown-item">
                    Home
                  </Link>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    Top Clasament
                  </a>
                </li>
                <li>
                  <a className="dropdown-item" href="#">
                    My Points
                  </a>
                </li>
              </ul>
            </li>
            <li className="nav-item me-3">
              <Link to="/login" className="nav-link">
                Login
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default Header;
