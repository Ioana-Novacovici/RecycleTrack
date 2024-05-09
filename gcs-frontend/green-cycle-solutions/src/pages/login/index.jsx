import React, { useState, useContext, useRef, useEffect } from "react";
import { Link } from "react-router-dom";
import { client } from "../../api/AuthenticationService";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../api/AuthProvider";

function Login() {
  const [username, setUsername] = useState("");
  const [isUsernameValid, setIsUsernameValid] = useState(true);
  const [usernameError, setUsernameError] = useState();
  const [password, setPassword] = useState("");
  const [isPasswordValid, setIsPasswordValid] = useState(true);
  const [passwordError, setPasswordError] = useState();
  const [errorMessage, setErrorMessage] = useState("");
  const { auth, setAuth } = useContext(AuthContext);
  const navigate = useNavigate();
  const errRef = useRef();

  useEffect(() => {
    setErrorMessage("");
  }, [username, password]);

  const handleLoginAction = async (e) => {
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
      try {
        let response = await client.post(
          "/login",
          {
            username: username,
            password: password,
          },
          {
            headers: {
              "Content-Type": "application/json",
            },
            withCredentials: true,
          }
        );
        console.log(response);
        const usernameContext = response.data.username;
        console.log(usernameContext);
        const passwordContext = response.data.password;
        const role = response.data.role;
        const gender = response.data.gender;
        setAuth({ usernameContext, passwordContext, role, gender });
        localStorage.setItem("user", JSON.stringify(auth));
        localStorage.setItem("session-key", password);
        navigate("/account");
      } catch (error) {
        if (error.response) {
          setErrorMessage("Bad credentials!");
        } else {
          setErrorMessage("Something went wrong. Please try again!");
          console.log(error);
        }
      }
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
      className="mx-auto mt-5 w-50 p-5 custom-form needs-validation rounded"
      style={{ background: "#bed0ab" }}
      noValidate
      onSubmit={handleLoginAction}
    >
      <h4
        className="text-center mt-3 mb-5 fw-normal fst-italic"
        style={{ color: "#354a3f" }}
      >
        Login
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
    </form>
  );
}

export default Login;
