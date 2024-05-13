import React, { useContext, useState, useEffect } from "react";
import CollectionCard from "./components";
import { collectionsClient } from "../../api/RequestService";
import AuthContext from "../../api/AuthProvider";

function UserDashboard() {
  const { auth } = useContext(AuthContext);
  const [collections, setCollections] = useState([]);
  const headerText = ["Collection Date", "Quantity", "Points", "Action"];
  // const collections = [
  //   {
  //     date: "22.05.2024",
  //     totalQuantity: 10,
  //     totalPoints: 20,
  //     collectionDetailsDTOList: [
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "METAL",
  //       },
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "PLASTIC",
  //       },
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "GLASS",
  //       },
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "PAPER",
  //       },
  //     ],
  //   },
  //   {
  //     date: "22.05.2024",
  //     totalQuantity: 10,
  //     totalPoints: 20,
  //     collectionDetailsDTOList: [
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "METAL",
  //       },
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "PLASTIC",
  //       },
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "GLASS",
  //       },
  //       {
  //         kilograms: 0.5,
  //         points: 12,
  //         recycledType: "PAPER",
  //       },
  //     ],
  //   },
  // ];

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
        console.log(response);
      } catch (error) {
        console.log(error);
        if (error.response) {
          // setError(error.response.data.message);
        } else {
          // setError("Something went wrong! Please try again later.");
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
      </div>
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

      {collections.map((collection, index) => (
        <CollectionCard key={index} collection={collection} />
      ))}
    </div>
  );
}

export default UserDashboard;
