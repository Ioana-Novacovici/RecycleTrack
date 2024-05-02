import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/header";
import Home from "./components/home";
import Login from "./components/login";
import Register from "./components/register";
import AgentDashboard from "./components/agent-dashboard";
import GuardedRoutes from "./components/route-guard";
import { useContext } from "react";
import AuthContext from "./api/AuthProvider";

function App() {
  const { auth } = useContext(AuthContext);
  console.log(auth.user, auth.role);

  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/register" element={<Register />}></Route>
          {/* <Route path="/dashboard-agent" element={<AgentDashboard />}></Route> */}
          {/* <Route element={<GuardedRoutes />}>
            <Route element={<AgentDashboard />} path="/dashboard-agent"></Route>
          </Route> */}
          <Route
            path="/dashboard-agent"
            element={
              auth.role === "AGENT" ? (
                <RequireAuth redirectTo="/login">
                  <ProtectedPage />
                </RequireAuth>
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          {/* <Route path="/dashboard-agent" element={<GuardedRoutes />} /> */}
        </Routes>
      </BrowserRouter>
    </>
  );
}

function RequireAuth({ children, redirectTo }) {
  let isAuthenticated = false;
  return isAuthenticated ? children : <Navigate to={redirectTo} />;
}

export default App;
