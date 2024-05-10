import React, { useEffect, useState, useContext } from "react";
import AddressCard from "./components";
import { addressesClient } from "../../api/RequestService.js";
import metal from "../../assets/images/metal.png";
import plastic from "../../assets/images/plastic.png";
import paper from "../../assets/images/paper.png";
import glass from "../../assets/images/glass.png";
import AuthContext from "../../api/AuthProvider";

function AgentDashboard() {
  const currentDate = new Date();
  const daysOfWeek = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];

  const images = [
    { src: metal, alt: "metal-logo" },
    { src: plastic, alt: "plastic-logo" },
    { src: paper, alt: "paper-logo" },
    { src: glass, alt: "glass-logo" },
  ];

  const formattedDate = currentDate.toISOString().split("T")[0];
  const currentDay = daysOfWeek[currentDate.getDay()];
  const [addresses, setAddresses] = useState([]);
  const [error, setError] = useState();
  const [collectionCodes, setCollectionCodes] = useState([]);
  const { auth } = useContext(AuthContext);

  useEffect(() => {
    const fetchCurrentDayAddresses = async () => {
      try {
        const key = localStorage.getItem("session-key");
        let response = await addressesClient.get(
          "/today",
          {
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
        setAddresses(response.data);
        const array = [];
        response.data.map((data) => array.push(data.collectionCode));
        console.log(array);
        setCollectionCodes(array);
        console.log(collectionCodes);
      } catch (error) {
        if (error.response) {
          setError(error.response.data.message);
        } else {
          setError("Something went wrong! Please try again later.");
        }
      }
    };
    fetchCurrentDayAddresses();
  }, []);

  return (
    <div>
      <div className="row m-5">
        <h5
          className="card-header fs-3 text fw-normal ms-3"
          style={{ color: "#354a3f" }}
        >
          <i className="fa-regular fa-calendar-days me-3"></i>
          Today's date: {currentDay}, {formattedDate}
        </h5>
      </div>
      {error ? (
        <div className="row m-5 pe-5 ps-3">
          <div className="alert alert-primary d-flex align-items-center">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              fill="currentColor"
              className="bi bi-exclamation-triangle-fill flex-shrink-0 me-2"
              viewBox="0 0 16 16"
              role="img"
              aria-label="Info:"
            >
              <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z" />
            </svg>
            <div>{error}</div>
          </div>
        </div>
      ) : (
        <div className="row mt-5 ms-5">
          <h3
            className="mb-3 ms-5 fs-3 text fw-normal"
            style={{ color: "#354a3f" }}
          >
            Recyclables collection locations
          </h3>
          <div className="col-6 ms-5">
            {addresses.map((address) => (
              <AddressCard key={address.collectionCode} address={address} />
            ))}
          </div>
          <div
            className="col-4 ms-5 rounded"
            style={{ backgroundColor: "#bed0ab" }}
          >
            <h3
              className="mb-3 mt-3 fs-3 text text-center fw-normal"
              style={{ color: "#354a3f" }}
            >
              Collection details
            </h3>
            <div className=" m-3">
              <label
                htmlFor="code"
                className="form-label fw-bold"
                style={{ color: "#354a3f" }}
              >
                Collection Code
              </label>
              <select className="form-select" defaultValue={"DEFAULT"}>
                <option value="DEFAULT" disabled>
                  Select the collection code
                </option>
                {collectionCodes.map((code) => (
                  <option key={code} value={code}>
                    {code}
                  </option>
                ))}
              </select>
              <div className="row">
                <label
                  htmlFor="materials"
                  className="form-label fw-bold mt-3"
                  style={{ color: "#354a3f" }}
                >
                  Recycled Materials
                </label>
              </div>
              {images.map((image, index) => (
                <div key={index} className="row">
                  <label className="mt-1">
                    <img
                      alt={image.alt}
                      src={image.src}
                      width="70"
                      height="70"
                    />
                  </label>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default AgentDashboard;
