import React, { useContext } from "react";
import AuthContext from "../../api/AuthProvider";
import CollectionCard from "./components";

function UserDashboard() {
  const { auth } = useContext(AuthContext);

  const headerText = ["Collection Date", "Quantity", "Points", "Action"];
  const collections = [
    {
      date: "22.05.2024",
      totalQuantity: 10,
      totalPoints: 20,
      collectionDetailsDTOList: [
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "METAL",
        },
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "PLASTIC",
        },
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "GLASS",
        },
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "PAPER",
        },
      ],
    },
    {
      date: "22.05.2024",
      totalQuantity: 10,
      totalPoints: 20,
      collectionDetailsDTOList: [
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "METAL",
        },
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "PLASTIC",
        },
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "GLASS",
        },
        {
          kilograms: 0.5,
          points: 12,
          recycledType: "PAPER",
        },
      ],
    },
  ];

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
