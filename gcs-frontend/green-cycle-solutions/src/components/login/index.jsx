import React, { useState } from "react";
import { Link } from "react-router-dom";

function Login() {
  const [inputValue, setInputValue] = useState("");
  const [isValid, setIsValid] = useState(true);

  const handleLoginAction = (e) => {
    e.preventDefault();
    if (inputValue.trim() === "") {
      setIsValid(false);
    } else {
      setIsValid(true);
      // Handle form submission here
    }
  };

  const handleChange = (e) => {
    setInputValue(e.target.value);
    setIsValid(true); // Reset validation when user starts typing
  };

  return (
    <form
      className="mx-auto mt-5 w-50 p-5 custom-form needs-validation"
      style={{ background: "#8fc23c", borderRadius: "20px" }}
      noValidate
      onSubmit={handleLoginAction}
    >
      <h4
        className="text-center mt-3 mb-5 fw-normal fst-italic"
        style={{ color: "#354a3f" }}
      >
        Login
      </h4>
      <div className="mb-3">
        <label
          htmlFor="username"
          className="form-label fw-bold"
          style={{ color: "#354a3f" }}
        >
          User name
        </label>
        <input
          type="text"
          className={`form-control ${
            !isValid
              ? "is-invalid"
              : isValid && inputValue.trim() !== ""
              ? "is-valid"
              : ""
          }`}
          id="username"
          placeholder="Enter username"
          value={inputValue}
          onChange={handleChange}
        />
        <div className="invalid-feedback">Username required.</div>
      </div>
      <div className="mb-3">
        <label
          htmlFor="password"
          className="form-label fw-bold"
          style={{ color: "#354a3f" }}
        >
          Password
        </label>
        <input
          type="password"
          // className={`form-control ${
          //   formData.passwordValid ? "is-valid" : "is-invalid"
          // }`}
          id="password"
          placeholder="Enter password"
          // value={formData.password}
          onChange={handleChange}
        />
        <div className="invalid-feedback">Please provide a password</div>
      </div>
      <div className="mb-3 ">
        <Link to="/register" className="form-label">
          Haven't registered yet? Request access here.
        </Link>
      </div>
      <button
        type="submit"
        className="btn w-100 fw-bold"
        style={{
          background: "#abb5aa",
          borderRadius: "5px",
          color: "#354a3f",
        }}
        // disabled={!formData.formValid}
      >
        Login
      </button>
    </form>
  );
}

export default Login;
