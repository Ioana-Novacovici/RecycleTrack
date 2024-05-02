import { Outlet, Navigate } from "react-router-dom";
import AuthContext from "../../api/AuthProvider";
import { useContext } from "react";

const GuardedRoutes = () => {
  const { auth } = useContext(AuthContext);
  console.log(auth.user, auth.role, auth.user !== "", auth.role === "AGENT");
  return auth.user !== undefined ? <Outlet /> : <Navigate to="/login" />;
};

export default GuardedRoutes;
