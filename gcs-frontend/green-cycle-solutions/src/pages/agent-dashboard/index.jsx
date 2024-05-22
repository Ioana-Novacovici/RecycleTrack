import React, { useEffect, useState, useContext } from "react";
import AddressCard from "./components";
import { addressesClientUrl } from "../../api/RequestService.js";
import { collectionsClientUrl } from "../../api/RequestService.js";
import axios from "../../api/AxiosConfig.js";
import metal from "../../assets/images/metal.png";
import plastic from "../../assets/images/plastic.png";
import paper from "../../assets/images/paper.png";
import glass from "../../assets/images/glass.png";

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
  const [formError, setFormError] = useState("");
  const [collectionCodes, setCollectionCodes] = useState([]);
  const [quantities, setQuantities] = useState(
    new Array(images.length).fill("0.5")
  );
  const [selectedCode, setSelectedCode] = useState("DEFAULT");
  const [succesMessage, setSuccesMessage] = useState();

  const handleSelectChange = (event) => {
    setSelectedCode(event.target.value);
  };

  useEffect(() => {
    setFormError("");
  }, [quantities, selectedCode]);

  useEffect(() => {
    setSuccesMessage("");
  }, [selectedCode]);

  const handleQuantityChange = (index, value) => {
    const newQuantities = [...quantities];
    newQuantities[index] = value;
    setQuantities(newQuantities);
  };

  useEffect(() => {
    const fetchCurrentDayAddresses = async () => {
      try {
        let response = await axios.get(addressesClientUrl + "/today", {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        });
        setAddresses(response.data);
        const array = [];
        response.data.map((data) => array.push(data.collectionCode));
        setCollectionCodes(array);
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

  const handleAddCollection = async () => {
    const types = ["METAL", "PLASTIC", "PAPER", "GLASS"];
    for (let quantity of quantities) {
      if (quantity === "") {
        setFormError("Fill in all quantities!");
        return;
      }
    }
    if (selectedCode === "DEFAULT") {
      setFormError("Select a collection code!");
      return;
    }
    const quantitiesMap = types.reduce((map, type, index) => {
      map[type] = quantities[index];
      return map;
    }, {});
    try {
      await axios.post(
        collectionsClientUrl,
        {
          collectionCode: selectedCode,
          quantities: quantitiesMap,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        }
      );
      setQuantities(new Array(quantities.length).fill(""));
      setSuccesMessage("Collection added succesfully!");
    } catch (error) {
      if (error.response) {
        if (error.response.status === 400) {
          setFormError("You already introduced a collection with this code!");
        } else if (error.response.status === 500) {
          setFormError("Invalid quantity value! Double value required!");
        } else {
          setFormError("Something went wrong! Please try again later.");
        }
      } else {
        setFormError("Something went wrong! Please try again later.");
      }
    }
  };

  return (
    <div>
      {error ? (
        <div className="row m-5 pe-5 ps-3">
          <h5
            className="card-header fs-3 text fw-normal mb-3"
            style={{ color: "#354a3f" }}
          >
            <i className="fa-regular fa-calendar-days me-3"></i>
            Today's date: {currentDay}, {formattedDate}
          </h5>
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
        <div className="row ms-5 mt-5">
          <div className="col-6 ms-5">
            <div>
              <h5
                className="card-header fs-3 text fw-normal mb-3"
                style={{ color: "#354a3f" }}
              >
                <i className="fa-regular fa-calendar-days me-3"></i>
                Today's date: {currentDay}, {formattedDate}
              </h5>

              <h3
                className="mb-3 mt-3 fs-4 text fw-normal"
                style={{ color: "#354a3f" }}
              >
                Recyclables collection locations
              </h3>
            </div>
            <div style={{ maxHeight: "60vh", overflowY: "auto" }}>
              {addresses.map((address) => (
                <AddressCard key={address.collectionCode} address={address} />
              ))}
            </div>
          </div>
          <div
            className="col-4 ms-5 mb-5 rounded"
            style={{ backgroundColor: "#bed0ab" }}
          >
            <h3
              className="mb-3 mt-3 fs-3 text text-center fw-normal"
              style={{ color: "#354a3f" }}
            >
              Collection details
            </h3>
            {formError ? (
              <div
                className="alert alert-danger d-flex align-items-center"
                role="alert"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  fill="currentColor"
                  className="bi flex-shrink-0 me-2"
                  viewBox="0 0 16 16"
                  role="img"
                  aria-label="Warning:"
                >
                  <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                </svg>
                <div>{formError}</div>
              </div>
            ) : succesMessage ? (
              <div
                className="alert alert-success d-flex align-items-center"
                role="alert"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  fill="currentColor"
                  className="bi flex-shrink-0 me-2"
                  viewBox="0 0 16 16"
                  role="img"
                  aria-label="Success:"
                >
                  <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z" />
                </svg>
                <div>{succesMessage}</div>
              </div>
            ) : (
              <div className="offscreen"></div>
            )}
            <div className=" m-3">
              <label
                htmlFor="code"
                className="form-label fw-bold mt-1"
                style={{ color: "#354a3f" }}
              >
                Collection Code
              </label>
              <select
                className="form-select"
                value={selectedCode}
                onChange={handleSelectChange}
              >
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
                  className="form-label fw-bold mt-3 mb-1"
                  style={{ color: "#354a3f" }}
                >
                  Recycled Materials
                </label>
              </div>
              {images.map((image, index) => (
                <div key={index} className="row">
                  <label className="col-4 mt-1">
                    <img
                      alt={image.alt}
                      src={image.src}
                      width="90"
                      height="70"
                    />
                  </label>
                  <div className="col-4 mt-3">
                    <input
                      type="text"
                      className="form-control"
                      id="quantity"
                      placeholder="Enter quantity"
                      value={quantities[index]}
                      onChange={(e) =>
                        handleQuantityChange(index, e.target.value)
                      }
                    />
                  </div>
                  <label
                    htmlFor="materials"
                    className="fw-bold col-2 mt-3"
                    style={{ color: "#354a3f" }}
                  >
                    Kilograms
                  </label>
                </div>
              ))}
              <button
                type="button"
                onClick={handleAddCollection}
                className="btn w-100 fw-bold mt-3"
                style={{
                  background: "#abb5aa",
                  borderRadius: "5px",
                  color: "#354a3f",
                }}
              >
                Save Collection
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default AgentDashboard;
