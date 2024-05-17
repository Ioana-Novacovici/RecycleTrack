import React, { useState, useContext, useEffect } from "react";
import FirstPlace from "../../assets/images/1stPlace.png";
import SecondPlace from "../../assets/images/2ndPlace.png";
import ThirdPlace from "../../assets/images/3rdPlace.png";
import { collectionsClientUrl } from "../../api/RequestService.js";
import axios from "../../api/AxiosConfig.js";
import AuthContext from "../../api/AuthProvider";

function Leaderboard() {
  const headerText = ["Rank", "User", "Quantity recycled"];
  const [collections, setCollections] = useState([]);
  const { auth } = useContext(AuthContext);
  const [error, setError] = useState();

  function getPictureSource(index) {
    console.log(index);
    if (index === 0) return FirstPlace;
    else if (index === 1) return SecondPlace;
    else if (index === 2) return ThirdPlace;
  }

  useEffect(() => {
    const fetchWeeklyCollections = async () => {
      try {
        let response = await axios.get(collectionsClientUrl + "/weekly", {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        });
        setCollections(response.data);
        console.log(response.data);
      } catch (error) {
        if (error.response) {
          setError(
            "The weekly collection process has not started yet. Please check again later!"
          );
        } else {
          setError("Something went wrong! Please try again later.");
        }
      }
    };
    fetchWeeklyCollections();
  }, []);

  return (
    <div className="container mb-5">
      <div className="row mt-5">
        <h5
          className="card-header fs-3 text text-center fw-normal mb-3"
          style={{ color: "#354a3f" }}
        >
          <i className="fa-solid fa-trophy me-3"></i>
          Weekly Recycling Leaderboard
          <i className="fa-solid fa-trophy ms-3"></i>
        </h5>
      </div>
      {!error ? (
        <div className="row mt-5">
          {headerText.map((text) => (
            <h5
              className="col fs-4 text fw-normal mb-3 text-center"
              style={{ color: "#354a3f" }}
              key={text}
            >
              {text}
            </h5>
          ))}
          <hr
            style={{
              borderColor: "#354a3f",
              borderWidth: "2px",
              borderStyle: "solid",
            }}
          />
        </div>
      ) : (
        <div className="mt-3 alert alert-primary d-flex align-items-center">
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
      )}
      {collections.map((collection, index) => (
        <div key={index} className="row mt-1 mb-1" style={{ color: "#354a3f" }}>
          <div className="col text text-center  mt-1">
            <h5 className="text-center">
              <img
                className="mt-1 mb-1"
                src={getPictureSource(index)}
                width="70"
                height="70"
              />
            </h5>
          </div>
          <div className="col text text-center  mt-3">
            <h5 className="fs-5 text fst-italic">{collection.username}</h5>
          </div>
          <div className="col text text-center  mt-3">
            <h5 className="fs-5 text fst-italic">
              <i
                className="fa-solid fa-weight-hanging me-3"
                style={{ color: "#9AA2E6" }}
              ></i>
              {collection.totalQuantity} kilograms
            </h5>
          </div>
        </div>
      ))}
    </div>
  );
}

export default Leaderboard;
