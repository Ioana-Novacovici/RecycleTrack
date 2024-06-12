import React, { useRef, useState, useEffect } from "react";
import { authClientUrl } from "../../api/RequestService.js";
import axios from "../../api/AxiosConfig.js";
import { useNavigate } from "react-router-dom";

function Register() {
  const [cnp, setCnp] = useState("");
  const [isCnpValid, setIsCnpValid] = useState(true);
  const [cnpError, setCnpError] = useState();
  const [firstName, setFirstName] = useState("");
  const [isFirstNameValid, setIsFirstNameValid] = useState(true);
  const [firstNameError, setFirstNameError] = useState();
  const [lastName, setLastName] = useState("");
  const [isLastNameValid, setIsLastNameValid] = useState(true);
  const [lastNameError, setLastNameError] = useState();
  const [email, setEmail] = useState("");
  const [isEmailValid, setIsEmailValid] = useState(true);
  const [EmailError, setEmailError] = useState();
  const [isChecked, setIsChecked] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const errRef = useRef();

  useEffect(() => {
    setErrorMessage("");
  }, [cnp, firstName, lastName, email, isChecked]);

  const handleRegisterAction = async (e) => {
    e.preventDefault();
    let cnpReggex = /^\d{13}$/;
    let isValid = true;
    if (cnp.trim() === "") {
      setIsCnpValid(false);
      setCnpError("Cnp required");
      isValid = false;
    } else if (!cnpReggex.test(cnp)) {
      setIsCnpValid(false);
      setCnpError("Cnp needs to have 13 digits");
      isValid = false;
    }
    if (firstName.trim() === "") {
      setIsFirstNameValid(false);
      setFirstNameError("First name required");
      isValid = false;
    }
    if (lastName.trim() === "") {
      setIsLastNameValid(false);
      setLastNameError("Last name required");
      isValid = false;
    }
    if (email.trim() === "") {
      setIsEmailValid(false);
      setEmailError("Email required");
      isValid = false;
    }
    if (!isChecked) {
      isValid = false;
    }
    if (isValid) {
      setIsCnpValid(true);
      setIsFirstNameValid(true);
      setIsLastNameValid(true);
      setIsEmailValid(true);
      setIsEmailValid(true);
      setIsChecked(true);
      try {
        let response = await axios.post(
          authClientUrl + "/account",
          {
            cnp: cnp,
            firstName: firstName,
            lastName: lastName,
            email: email,
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
            withCredentials: true,
          }
        );
        navigate("/login");
      } catch (error) {
        if (error.response) {
          setErrorMessage(error.response.data.message);
        } else {
          setErrorMessage("Something went wrong. Please try again!");
        }
      }
    }
  };

  function validateCnp() {
    return !isCnpValid
      ? "is-invalid"
      : isCnpValid && cnp.trim() !== ""
      ? "is-valid"
      : "";
  }

  function validateFirstName() {
    return !isFirstNameValid
      ? "is-invalid"
      : isFirstNameValid && firstName.trim() !== ""
      ? "is-valid"
      : "";
  }

  function validateLastName() {
    return !isLastNameValid
      ? "is-invalid"
      : isLastNameValid && lastName.trim() !== ""
      ? "is-valid"
      : "";
  }

  function validateEmail() {
    return !isEmailValid
      ? "is-invalid"
      : isEmailValid && email.trim() !== ""
      ? "is-valid"
      : "";
  }

  function validateCheck() {
    return !isChecked ? "is-invalid" : isChecked ? "is-valid" : "";
  }

  const handleCnpChange = (e) => {
    setCnp(e.target.value);
    setIsCnpValid(true);
  };

  const handleFirstNameChange = (e) => {
    setFirstName(e.target.value);
    setIsFirstNameValid(true);
  };

  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
    setIsLastNameValid(true);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
    setIsEmailValid(true);
  };

  const handleCheckChange = () => {
    setIsChecked(!isChecked);
  };

  return (
    <form
      className="mx-auto mt-5 w-50 p-5 custom-form mb-5 rounded"
      style={{ background: "#bed0ab" }}
      noValidate
      onSubmit={handleRegisterAction}
    >
      <h4
        className="text-center mt-3 mb-5 fw-normal fst-italic"
        style={{ color: "#354a3f" }}
      >
        Request account activation
      </h4>
      {errorMessage ? (
        <div
          ref={errRef}
          className="alert alert-danger d-flex align-items-center"
          role="alert"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            fill="currentColor"
            className="bi bi-exclamation-triangle-fill flex-shrink-0 me-2"
            viewBox="0 0 16 16"
            role="img"
            aria-label="Warning:"
          >
            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
          </svg>
          <div>{errorMessage}</div>
        </div>
      ) : (
        <div className="offscreen"></div>
      )}
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
          className={`form-control ${validateCnp()}`}
          id="cnp"
          placeholder="Enter Personal Numeric Code"
          value={cnp}
          onChange={handleCnpChange}
        />
        <div className="invalid-feedback">{cnpError}</div>
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
            className={`form-control ${validateFirstName()}`}
            id="firstName"
            placeholder="Enter first name"
            value={firstName}
            onChange={handleFirstNameChange}
          />
          <div className="invalid-feedback">{firstNameError}</div>
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
            className={`form-control ${validateLastName()}`}
            id="lastName"
            placeholder="Enter last name"
            value={lastName}
            onChange={handleLastNameChange}
          />
          <div className="invalid-feedback">{lastNameError}</div>
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
          className={`form-control ${validateEmail()}`}
          id="email"
          placeholder="Enter email"
          value={email}
          onChange={handleEmailChange}
        />
        <div className="invalid-feedback">{EmailError}</div>
        <small id="emailHelp" className="form-text text-muted mb-3">
          You'll receive your activation account credentials on this address .
        </small>
      </div>
      <div className="form-check mb-3">
        <input
          type="checkbox"
          className={`form-check-input ${validateCheck()}`}
          id="exampleCheck1"
          checked={isChecked}
          onChange={handleCheckChange}
        />
        <label className="form-check-label" htmlFor="exampleCheck1">
          By submitting this form, you agree that the personal information
          provided will be processed by RecycleTrack for your account
          activation. Your information is confidentiall and will only be used
          for the intended purpose.
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
