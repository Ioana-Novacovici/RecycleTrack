import axios from "axios";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "./AuthProvider";

export const client = axios.create({
  baseURL: "http://localhost:8080/auth",
});

export const useLogout = () => {
  const { auth, setAuth } = useContext(AuthContext);
  const navigate = useNavigate();
  const logout = async () => {
    try {
      let response = await client.post("/logout");
      console.log(response);
      localStorage.removeItem("user");
      localStorage.removeItem("session-key");
      setAuth({});
      console.log(auth);
      navigate("/login");
    } catch (error) {
      console.log(error);
    }
  };
  return logout;
};
