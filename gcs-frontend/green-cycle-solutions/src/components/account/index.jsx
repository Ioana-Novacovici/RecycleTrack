import React, { useContext, useEffect } from "react";
import AvatarMale from "../../assets/images/UserAvatarMale.png";
import AvatarFemale from "../../assets/images/UserAvatarFemale.png";
import AuthContext from "../../api/AuthProvider";
import { addressesClient } from "../../api/RequestService.js";

function Account() {
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    const fetchData = async () => {
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
        console.log(response);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []);

  return (
    <div className="container mt-5 ms-5 p-5">
      <div className="row justify-content-md-center">
        <div className="col-4 ms-5 p-5">
          {auth.gender && auth.gender === "FEMALE" ? (
            <div>
              <img
                className="rounded-circle"
                alt=""
                src={AvatarFemale}
                width="250"
                height="250"
              ></img>
            </div>
          ) : (
            <div>
              <img
                className="rounded-circle"
                alt=""
                src={AvatarMale}
                width="250"
                height="250"
              ></img>
            </div>
          )}
        </div>
        <div
          className="col-6 p-5 rounded-top"
          style={{ backgroundColor: "#bed0ab" }}
        >
          <h3
            className="mt-3 mb-5 fw-normal fst-italic"
            style={{ color: "#354a3f" }}
          >
            Welcome back, {auth.usernameContext}!
          </h3>
          {/* <h4 className="mt-1 mb-3 fw-normal fs-4" style={{ color: "#354a3f" }}>
            Your {auth.role} account details
          </h4> */}
          <label
            htmlFor="email"
            className="form-label fw-bold"
            style={{ color: "#354a3f" }}
          >
            Change username
          </label>
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Enter your new username"
              aria-describedby="button-addon2"
            />
            <button
              className="btn btn-outline-secondary"
              type="button"
              id="button-addon2"
            >
              Change
            </button>
          </div>
          <label
            htmlFor="email"
            className="form-label fw-bold"
            style={{ color: "#354a3f" }}
          >
            Change password
          </label>
          <div className="input-group mb-3">
            <input
              type="password"
              className="form-control"
              placeholder="Enter your new password"
              aria-describedby="button-addon2"
            />
            <button
              className="btn btn-outline-secondary"
              type="button"
              id="button-addon2"
            >
              Change
            </button>
          </div>
          <small id="changeCredentials" className="form-text text-muted mb-3">
            Each of theese actions will log you out automatically!
          </small>
        </div>
      </div>
    </div>
  );
}

export default Account;
