import React from "react";

function AddressCard({ address }) {
  if (!address) {
    return <div className="offscreen"></div>;
  }

  return (
    <div className="card mb-3">
      <h5
        className="card-header fs-6 text fst-italic"
        style={{ color: "#354a3f", backgroundColor: "#bed0ab" }}
      >
        Collection Code: {address.collectionCode}
      </h5>
      <div
        className="card-body"
        style={{ color: "#988445", backgroundColor: "#faf9f8" }}
      >
        <h5 className="card-title fs-5">
          <i
            className="fa-solid fa-house me-3"
            style={{ color: "#354a3f" }}
          ></i>
          Pick-up address: {address.street} Street, Nr. {address.streetNumber}
          {address.block ? (
            ", Block " + address.block
          ) : (
            <div className="offscreen"></div>
          )}
          {address.apartmentNumber ? (
            ", Ap No. " + address.apartmentNumber
          ) : (
            <div className="offscreen"></div>
          )}
        </h5>
      </div>
    </div>
  );
}

export default AddressCard;
