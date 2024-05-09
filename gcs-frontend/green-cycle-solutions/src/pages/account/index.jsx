import React, { useContext, useEffect, useState, useRef } from "react";
import AvatarMale from "../../assets/images/UserAvatarMale.png";
import AvatarFemale from "../../assets/images/UserAvatarFemale.png";
import AuthContext from "../../api/AuthProvider";
import { addressesClient } from "../../api/RequestService.js";
import { useNavigate } from "react-router-dom";
import { client } from "../../api/AuthenticationService";
import "../account/style.css";

function Account() {
  const { auth } = useContext(AuthContext);
  const [address, setAddress] = useState({});
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const errRef = useRef();

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleUsernameSubmit = async (e) => {
    e.preventDefault();
    try {
      let response = await client.post(
        "/username",
        {
          oldUsername: auth.usernameContext,
          newUsername: username,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        }
      );
      console.log(response);
      localStorage.removeItem("user");
      localStorage.removeItem("session-key");
      navigate("/login");
    } catch (error) {
      if (error.response) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage("Something went wrong. Please try again!");
        console.log(error);
      }
    }
  };

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();
    console.log(password);
    const req = {
      username: auth.usernameContext,
      newPassword: password,
    };
    console.log(req);
    try {
      let response = await client.post(
        "/password",
        {
          username: auth.usernameContext,
          newPassword: password,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        }
      );
      console.log(response);
      localStorage.removeItem("user");
      localStorage.removeItem("session-key");
      navigate("/login");
    } catch (error) {
      if (error.response) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage("Something went wrong. Please try again!");
        console.log(error);
      }
    }
  };

  useEffect(() => {
    setErrorMessage("");
  }, [username, password]);

  useEffect(() => {
    const fetchUserAddress = async () => {
      try {
        const key = localStorage.getItem("session-key");
        let response = await addressesClient.get(
          "",
          {
            params: {
              username: auth.usernameContext,
            },
            auth: {
              username: auth.usernameContext,
              password: key,
            },
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
            withCredentials: true,
          }
        );
        setAddress(response.data[0]);
      } catch (error) {
        console.log(error);
      }
    };
    fetchUserAddress();
  }, []);

  return (
    <div className="container ms-5 p-5">
      <div className="row justify-content-md-center">
        <div className="col-4 ms-5 p-5 border border-2 rounded-start border-color">
          <div>
            <img
              className="rounded-circle"
              alt=""
              src={
                auth.gender && auth.gender === "FEMALE"
                  ? AvatarFemale
                  : AvatarMale
              }
              width="300"
              height="300"
            ></img>
          </div>
          <h3
            className="mt-3 fs-7 text text-center"
            style={{ color: "#354a3f" }}
          >
            Welcome back, {auth.usernameContext}!
          </h3>
        </div>
        <div
          className="col-6 p-5 rounded-end"
          style={{ backgroundColor: "#bed0ab" }}
        >
          <h3 className="mt-1 mb-3 fw-normal " style={{ color: "#354a3f" }}>
            Account Details
          </h3>
          {auth.role === "USER" ? (
            <div>
              <h3 className="mb-3 fw-normal " style={{ color: "#354a3f" }}>
                <i className="fa-solid fa-house me-3"></i>
                {address.street} Street {address.streetNumber}
                {address.block != null ? (
                  ", Block " + address.block
                ) : (
                  <div className="offscreen"></div>
                )}
                {address.apartmentNumber != null ? (
                  ", Ap no. " + address.apartmentNumber
                ) : (
                  <div className="offscreen"></div>
                )}
              </h3>
              <h3 className="mt-1 fs-bolder fs-5" style={{ color: "#354a3f" }}>
                <i className="fa-solid fa-recycle me-3"></i>
                Weekly collecting pick-up day: {address.day}
              </h3>
              <h3 className="mt-1 mb-3 fs-5" style={{ color: "#354a3f" }}>
                <i className="fa-solid fa-recycle me-3"></i>
                Code: {address.collectingCode}
              </h3>
            </div>
          ) : (
            <div class="offscreen" />
          )}
          <h3 className="mt-3 mb-3 fs-5" style={{ color: "#354a3f" }}>
            Edit your profile
          </h3>
          {errorMessage ? (
            <div
              ref={errRef}
              className="alert alert-danger d-flex align-items-center"
              role="alert"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                fill="currentColor"
                className="bi bi-exclamation-triangle-fill flex-shrink-0 me-2"
                viewBox="0 0 16 16"
                role="img"
                aria-label="Warning:"
              >
                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
              </svg>
              <div>{errorMessage}</div>
            </div>
          ) : (
            <div className="offscreen"></div>
          )}
          <label
            htmlFor="username"
            className="form-label fw-bold"
            style={{ color: "#354a3f" }}
          >
            Change username
          </label>
          <form className="input-group mb-3" onSubmit={handleUsernameSubmit}>
            <input
              type="text"
              id="username"
              className="form-control"
              placeholder="Enter your new username"
              value={username}
              onChange={handleUsernameChange}
            />
            <button
              className="btn btn-outline-secondary"
              type="submit"
              id="button1"
              disabled={!username.trim()}
            >
              Reset
            </button>
          </form>
          <label
            htmlFor="password"
            className="form-label fw-bold"
            style={{ color: "#354a3f" }}
          >
            Change password
          </label>
          <form className="input-group mb-3" onSubmit={handlePasswordSubmit}>
            <input
              type="password"
              id="password"
              className="form-control"
              value={password}
              onChange={handlePasswordChange}
              placeholder="Enter your new password"
            />
            <button
              className="btn btn-outline-secondary"
              type="submit"
              id="button2"
              disabled={!password.trim()}
            >
              Reset
            </button>
          </form>
          <small id="changeCredentials" className="form-text text-muted mb-3">
            Each of theese actions will log you out automatically!
          </small>
        </div>
      </div>
    </div>
  );
}

export default Account;
