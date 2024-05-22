import React, { useState, useContext } from "react";
import Barcode from "react-barcode";
import glassCollection from "../../../assets/images/glassCollection.png";
import paperCollection from "../../../assets/images/paperCollection.png";
import metalCollection from "../../../assets/images/metalCollection.png";
import plasticCollection from "../../../assets/images/plasticCollection.png";
import AuthContext from "../../../api/AuthProvider";
import axios from "../../../api/AxiosConfig.js";
import { collectionsClientUrl } from "../../../api/RequestService.js";

function CollectionCard({ collection }) {
  if (!collection) {
    return <div className="offscreen"></div>;
  }

  const details = collection.collectionDetailsDTOList;
  const { auth } = useContext(AuthContext);
  const [used, setUsed] = useState(() => {
    return collection.isUsed;
  });
  const [buttonText, setButtonText] = useState(() => {
    if (collection.isUsed === false) {
      return "Use Points";
    } else {
      return "Points Used";
    }
  });
  const barCodeInformation =
    "Discount amount: " + collection.totalPoints + " RON";

  function getPictureSource(detail) {
    if (detail.recycledType === "GLASS") return glassCollection;
    else if (detail.recycledType === "PAPER") return paperCollection;
    else if (detail.recycledType === "METAL") return metalCollection;
    else if (detail.recycledType === "PLASTIC") return plasticCollection;
  }

  const handleUsePointsAction = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        collectionsClientUrl + "/use",
        {
          date: collection.date,
          username: auth.usernameContext,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
          withCredentials: true,
        }
      );
      setUsed(true);
      setButtonText("Points Used");
    } catch (error) {}
  };

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
            onClick={handleUsePointsAction}
            disabled={used}
            data-bs-toggle="modal"
            data-bs-target="#barCodeModal"
          >
            {buttonText}
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

      <div
        className="modal fade"
        id="barCodeModal"
        tabIndex="-1"
        aria-labelledby="barCodeModal"
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id="barCodeModal">
                <i className="fa-solid fa-recycle me-3"></i>
                Congrats for your recycling progress!
              </h5>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <h6>
                Make sure you save this barcode. Enjoy your shopping discount!
              </h6>
              <Barcode
                className="ms-1 mt-3"
                value={barCodeInformation}
                width={1.5}
                height={100}
                displayValue={false}
                background="#bed0ab"
              />
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CollectionCard;
