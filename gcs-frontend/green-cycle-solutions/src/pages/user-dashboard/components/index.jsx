import React from "react";
import glassCollection from "../../../assets/images/glassCollection.png";
import paperCollection from "../../../assets/images/paperCollection.png";
import metalCollection from "../../../assets/images/metalCollection.png";
import plasticCollection from "../../../assets/images/plasticCollection.png";

function CollectionCard({ collection }) {
  if (!collection) {
    return <div className="offscreen"></div>;
  }

  const details = collection.collectionDetailsDTOList;

  function getPictureSource(detail) {
    if (detail.recycledType === "GLASS") return glassCollection;
    else if (detail.recycledType === "PAPER") return paperCollection;
    else if (detail.recycledType === "METAL") return metalCollection;
    else if (detail.recycledType === "PLASTIC") return plasticCollection;
  }

  return (
    <div>
      <div className="row mt-1 mb-1" style={{ color: "#354a3f" }}>
        <div className="col text text-center mt-1">
          <h5 className="fs-5 text fst-italic">
            <i
              className="fa-regular fa-calendar-days me-3"
              style={{ color: "#0B59A2" }}
            ></i>
            {collection.date}
          </h5>
        </div>
        <div className="col text text-center  mt-1">
          <h5 className="fs-5 text fst-italic">
            <i
              className="fa-solid fa-weight-hanging me-3"
              style={{ color: "#9AA2E6" }}
            ></i>
            {collection.totalQuantity} kilograms
          </h5>
        </div>
        <div className="col text text-center  mt-1">
          <h5 className="fs-5 text fst-italic">
            <i
              className="fa-solid fa-coins me-3"
              style={{ color: "#E8A219" }}
            ></i>
            {collection.totalPoints} RON
          </h5>
        </div>
        <div className="col mb-1">
          <button
            type="button"
            className="btn w-100 fw-bold"
            style={{
              background: "#abb5aa",
              borderRadius: "5px",
              color: "#354a3f",
            }}
          >
            Use Points
          </button>
        </div>
      </div>
      <div className="row">
        {details.map((detail, index) => (
          <div
            className="col-6 mt-1 mb-1 d-flex"
            style={{ color: "#354a3f", background: "#bed0ab " }}
            key={index}
          >
            <img
              className="mt-1 mb-1 ms-5"
              src={getPictureSource(detail)}
              width="70"
              height="70"
            />
            <span className="col-1"></span>
            <h5 className="fs-5 text fst-italic ms-3 mt-3 text-centered">
              {detail.recycledType} - {detail.quantity} kilograms
            </h5>
          </div>
        ))}
      </div>
      <hr
        style={{
          borderColor: "#354a3f",
          borderWidth: "2px",
          borderStyle: "solid",
        }}
      />
    </div>
  );
}

export default CollectionCard;
