import axios from "axios";
import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "./AuthProvider";

const client = axios.create({
  baseURL: "http://localhost:8080/auth",
});

export const useLogin = () => {
  const { setAuth } = useContext(AuthContext);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const login = async (username, password) => {
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
      const user = response.data.username;
      const role = response.data.role;
      localStorage.setItem("user", JSON.stringify(response.data));
      setAuth({ user, role });
      navigate("/");
    } catch (error) {
      if (error.response) {
        setError("The username or the password is not correct");
      } else {
        setError("Something went wrong. Please try again!");
        console.log(error);
      }
    }
  };
  return { login, error };
};

export const useLogout = () => {
  const { auth, setAuth } = useContext(AuthContext);
  const navigate = useNavigate();
  const logout = async () => {
    try {
      let response = await client.post("/logout");
      console.log(response);
      localStorage.removeItem("user");
      setAuth({});
      console.log(auth);
      navigate("/login");
    } catch (error) {
      console.log(error);
    }
  };
  return logout;
};
