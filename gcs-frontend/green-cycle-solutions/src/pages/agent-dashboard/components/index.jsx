import React from "react";

function AddressCard() {
  return (
    <div className="card mb-3">
      <h5
        className="card-header fs-5 text fw-normal"
        style={{ color: "#354a3f", backgroundColor: "#bed0ab" }}
      >
        Collecting Code:
      </h5>
      <div
        className="card-body"
        style={{ color: "#988445", backgroundColor: "#faf9f8" }}
      >
        <h5 className="card-title fs-4">
          <i className="fa-solid fa-house me-3"></i>
          Address:
        </h5>
      </div>
    </div>
  );
}

export default AddressCard;
