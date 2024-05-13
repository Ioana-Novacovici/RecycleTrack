import React, { useContext, useState, useEffect } from "react";
import CollectionCard from "./components";
import { collectionsClient } from "../../api/RequestService";
import AuthContext from "../../api/AuthProvider";

function UserDashboard() {
  const { auth } = useContext(AuthContext);
  const [collections, setCollections] = useState([]);
  const headerText = ["Collection Date", "Quantity", "Points", "Action"];
  const [error, setError] = useState();

  useEffect(() => {
    const fetchUserCollections = async () => {
      try {
        const key = localStorage.getItem("session-key");
        let response = await collectionsClient.get(
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
        setCollections(response.data);
      } catch (error) {
        if (error.response) {
          setError("Your collection list is empty for now!");
        } else {
          setError("Something went wrong! Please try again later.");
        }
      }
    };
    fetchUserCollections();
  }, []);

  return (
    <div className="container mb-5">
      <div className="row mt-5">
        <h5
          className="card-header fs-3 text fw-normal mb-3"
          style={{ color: "#354a3f" }}
        >
          <i className="fa-solid fa-recycle me-3"></i>
          My Collections
        </h5>
        {error ? (
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
        ) : (
          <div className="offscreen"> </div>
        )}
      </div>
      {!error ? (
        <div className="row mt-3">
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
        <div className="offscreen"> </div>
      )}
      {collections.map((collection, index) => (
        <CollectionCard key={index} collection={collection} />
      ))}
    </div>
  );
}

export default UserDashboard;
