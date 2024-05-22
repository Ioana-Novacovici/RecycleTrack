import axios from "./AxiosConfig";
import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "./AuthProvider";

export const useLogout = () => {
  const { setAuth } = useContext(AuthContext);
  const navigate = useNavigate();
  const logout = async () => {
    try {
      let response = await axios.post("http://localhost:8080/auth/logout");
      localStorage.removeItem("user");
      localStorage.removeItem("token");
      setAuth({});
      navigate("/login");
    } catch (error) {}
  };
  return logout;
};
