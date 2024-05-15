import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Header from "./pages/header";
import Home from "./pages/home";
import Login from "./pages/login";
import Register from "./pages/register";
import AgentDashboard from "./pages/agent-dashboard";
import { useContext } from "react";
import AuthContext from "./api/AuthProvider";
import UserDashboard from "./pages/user-dashboard";
import Account from "./pages/account";
import Leaderboard from "./pages/leaderboard";

function App() {
  const { auth } = useContext(AuthContext);

  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/register" element={<Register />}></Route>
          <Route
            path="/dashboard-agent"
            element={
              auth.role === "AGENT" ? (
                <AgentDashboard />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          <Route
            path="/dashboard-user"
            element={
              auth.role === "USER" ? (
                <UserDashboard />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          <Route
            path="/leaderboard"
            element={
              auth.role === "USER" ? <Leaderboard /> : <Navigate to="/login" />
            }
          />
          <Route
            path="/account"
            element={
              auth.usernameContext ? <Account /> : <Navigate to="/login" />
            }
          />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
