import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useLogin } from "../../api/AuthenticationService";
import ErrorModal from "../error-modal";

function Login() {
  const [username, setUsername] = useState("");
  const [isUsernameValid, setIsUsernameValid] = useState(true);
  const [usernameError, setUsernameError] = useState();
  const [password, setPassword] = useState("");
  const [isPasswordValid, setIsPasswordValid] = useState(true);
  const [passwordError, setPasswordError] = useState();
  const { login, error } = useLogin();

  const handleLoginAction = (e) => {
    e.preventDefault();
    let isValid = true;
    if (username.trim() === "") {
      setIsUsernameValid(false);
      setUsernameError("Username required");
      isValid = false;
    }
    if (password.trim() === "") {
      setIsPasswordValid(false);
      setPasswordError("Password required");
      isValid = false;
    }
    if (isValid) {
      setIsUsernameValid(true);
      setIsPasswordValid(true);
      login(username, password);
    }
  };

  function validateUsername() {
    return !isUsernameValid
      ? "is-invalid"
      : isUsernameValid && username.trim() !== ""
      ? "is-valid"
      : "";
  }

  function validatePassword() {
    return !isPasswordValid
      ? "is-invalid"
      : isPasswordValid && password.trim() !== ""
      ? "is-valid"
      : "";
  }

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
    setIsUsernameValid(true);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
    setIsPasswordValid(true);
  };

  return (
    <form
      className="mx-auto mt-5 w-50 p-5 custom-form needs-validation"
      style={{ background: "#8fc23c", borderRadius: "20px" }}
      noValidate
      onSubmit={handleLoginAction}
    >
      {error && (
        <ErrorModal errorTitle={"Login Failure"} errorMessage={error} />
      )}
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
          className={`form-control ${validateUsername()}`}
          id="username"
          placeholder="Enter username"
          value={username}
          onChange={handleUsernameChange}
        />
        <div className="invalid-feedback">{usernameError}</div>
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
          className={`form-control ${validatePassword()}`}
          id="password"
          placeholder="Enter password"
          value={password}
          onChange={handlePasswordChange}
        />
        <div className="invalid-feedback">{passwordError}</div>
      </div>
      <div className="mb-3 ">
        <Link to="/register" className="form-label">
          Haven't registered yet? Request access here.
        </Link>
      </div>
      {error ? (
        <button
          type="submit"
          className="btn w-100 fw-bold"
          data-bs-toggle="modal"
          data-bs-target="#exampleModal"
          style={{
            background: "#abb5aa",
            borderRadius: "5px",
            color: "#354a3f",
          }}
        >
          Login
        </button>
      ) : (
        <button
          type="submit"
          className="btn w-100 fw-bold"
          style={{
            background: "#abb5aa",
            borderRadius: "5px",
            color: "#354a3f",
          }}
        >
          Login
        </button>
      )}
    </form>
  );
}

export default Login;
