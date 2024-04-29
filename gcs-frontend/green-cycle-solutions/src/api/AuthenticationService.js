import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const client = axios.create({
  baseURL: "http://localhost:8080/auth/login",
});

export const useLogin = () => {
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const login = async (username, password) => {
    setError(null);
    try {
      let response = await client.post("", {
        username: username,
        password: password,
      });
      navigate("/");
      // Handle successful response
      console.log(response);
    } catch (error) {
      if (error.response) {
        setError("The username or the password is not correct");
      } else {
        setError("Something went wrong. Please try again!");
      }
    }
  };
  return { login, error };
};
