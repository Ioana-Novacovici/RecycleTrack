import React, { useContext } from "react";
import LogoImage from "../../assets/images/RT_logo.png";
import { Link } from "react-router-dom";
import "../header/style.css";
import AuthContext from "../../api/AuthProvider";
import { useLogout } from "../../api/AuthenticationService";

function Header() {
  const { auth } = useContext(AuthContext);
  const logout = useLogout();

  const handleLogoutAction = (e) => {
    e.preventDefault();
    logout();
  };

  return (
    <nav className="navbar navbar-expand-lg bg-body-header">
      <div className="container-fluid ">
        <img src={LogoImage} alt="RT" width="4%" height="4%" />
        <div className="navbar-brand fs-4 ms-4">RecycleTrack </div>
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
                {auth.role === "USER" ? (
                  <div>
                    <li>
                      <Link to="/dashboard-user" className="dropdown-item">
                        My Collections
                      </Link>
                    </li>
                    <li>
                      <Link to="/leaderboard" className="dropdown-item">
                        Leaderboard
                      </Link>
                    </li>
                  </div>
                ) : auth.role === "AGENT" ? (
                  <li>
                    <Link to="/dashboard-agent" className="dropdown-item">
                      Dashboard
                    </Link>
                  </li>
                ) : (
                  <div className="offscreen"></div>
                )}
                {auth.usernameContext ? (
                  <li>
                    <Link to="/account" className="dropdown-item">
                      My Account
                    </Link>
                  </li>
                ) : (
                  <div className="offscreen"></div>
                )}
              </ul>
            </li>
            {auth.usernameContext ? (
              <li className="nav-item me-3">
                <a className="nav-link" onClick={handleLogoutAction}>
                  Logout
                </a>
              </li>
            ) : (
              <li className="nav-item me-3">
                <Link to="/login" className="nav-link">
                  Login
                </Link>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default Header;
