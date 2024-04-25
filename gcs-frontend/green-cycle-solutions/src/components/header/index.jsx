import React from "react";
import LogoImage from "../../assets/images/gcs_logo.png";

function Header() {
  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary">
      <div className="container-fluid">
        <img src={LogoImage} alt="GCS" width="5%" height="5%" />
        <a className="navbar-brand" href="#">
          Green Cycle Solutions
        </a>
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
          <ul className="navbar-nav grid gap-4">
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
                  <a className="dropdown-item" href="#">
                    Home
                  </a>
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
              <a className="nav-link active" href="#">
                Login
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default Header;
