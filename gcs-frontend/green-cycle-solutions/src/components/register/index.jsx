import React from "react";

function Register() {
  return (
    <form
      className="mx-auto mt-5 w-50 p-5 custom-form mb-5"
      style={{ background: "#8fc23c", borderRadius: "20px" }}
    >
      <h4
        className="text-center mt-3 mb-5 fw-normal fst-italic"
        style={{ color: "#354a3f" }}
      >
        Request your account activation
      </h4>
      <div className="mb-3">
        <label
          htmlFor="cnp"
          className="form-label fw-bold"
          style={{ color: "#354a3f" }}
        >
          CNP
        </label>
        <input
          type="text"
          className="form-control"
          id="cnp"
          placeholder="Enter Personal Numeric Code"
        />
      </div>
      <div className="row mb-3">
        <div className="form-group col">
          <label
            htmlFor="firstName"
            className="form-label fw-bold"
            style={{ color: "#354a3f" }}
          >
            First Name
          </label>
          <input
            type="text"
            className="form-control"
            id="firstName"
            placeholder="Enter first name"
          />
        </div>
        <div className="form-group col">
          <label
            htmlFor="lastName"
            className="form-label fw-bold"
            style={{ color: "#354a3f" }}
          >
            Last Name
          </label>
          <input
            type="text"
            className="form-control"
            id="lastName"
            placeholder="Enter last name"
          />
        </div>
      </div>
      <div className="form-group mb-3">
        <label
          htmlFor="email"
          className="form-label fw-bold"
          style={{ color: "#354a3f" }}
        >
          Email address
        </label>
        <input
          type="email"
          className="form-control"
          id="email"
          aria-describedby="emailHelp"
          placeholder="Enter email"
        />
        <small id="emailHelp" className="form-text text-muted mb-3">
          We'll never share your email with anyone else.
        </small>
      </div>
      <div className="form-check mb-3">
        <input
          type="checkbox"
          className="form-check-input"
          id="exampleCheck1"
        />
        <label className="form-check-label" for="exampleCheck1">
          By submitting this form, you agree that the personal information
          provided will be processed by GCS for your account activation. Your
          information is confidentiall and will only be used for the intended
          purpose.
        </label>
      </div>
      <button
        type="submit"
        className="btn w-100 fw-bold"
        style={{
          background: "#abb5aa",
          borderRadius: "5px",
          color: "#354a3f",
        }}
      >
        Submit Activation Form
      </button>
    </form>
  );
}

export default Register;
