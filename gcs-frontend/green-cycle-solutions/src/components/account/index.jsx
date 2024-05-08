import React, { useContext, useEffect, useState } from "react";
import AvatarMale from "../../assets/images/UserAvatarMale.png";
import AvatarFemale from "../../assets/images/UserAvatarFemale.png";
import AuthContext from "../../api/AuthProvider";
import { addressesClient } from "../../api/RequestService.js";
import "../account/style.css";

function Account() {
  const { auth } = useContext(AuthContext);
  const [address, setAddress] = useState({});

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
        setAddress(response.data[0]);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
  }, []);

  return (
    <div className="container ms-5 p-5">
      <div className="row justify-content-md-center">
        <div className="col-4 ms-5 p-5 border border-2 rounded-start border-color">
          {auth.gender && auth.gender === "FEMALE" ? (
            <div>
              <img
                className="rounded-circle"
                alt=""
                src={AvatarFemale}
                width="300"
                height="300"
              ></img>
            </div>
          ) : (
            <div>
              <img
                className="rounded-circle"
                alt=""
                src={AvatarMale}
                width="300"
                height="300"
              ></img>
            </div>
          )}
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
          <h3 className="mt-3 mb-3 fs-5" style={{ color: "#354a3f" }}>
            Edit your profile
          </h3>
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
