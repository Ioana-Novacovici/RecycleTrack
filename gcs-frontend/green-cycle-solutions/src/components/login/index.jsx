import React from "react";

function Login() {
  return (
    <form
      className="mx-auto mt-5 w-50 p-5 custom-form"
      style={{ background: "#8fc23c", borderRadius: "20px" }}
    >
      <h4
        className="text-center mt-3 mb-5 fw-normal fst-italic"
        style={{ color: "#354a3f" }}
      >
        Login
      </h4>
      <div className="mb-3">
        <label className="form-label fw-bold" style={{ color: "#354a3f" }}>
          User name
        </label>
        <input type="text" className="form-control" id="username" />
      </div>
      <div className="mb-3">
        <label className="form-label fw-bold" style={{ color: "#354a3f" }}>
          Password
        </label>
        <input type="password" className="form-control" id="password" />
      </div>
      <div className="mb-3 ">
        <a className="form-label">
          Haven't registered yet? Request access here.
        </a>
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
        Submit
      </button>
    </form>
  );
}

export default Login;
