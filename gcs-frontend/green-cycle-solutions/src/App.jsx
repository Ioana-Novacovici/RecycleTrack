import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/header";
import Home from "./components/home";
import Login from "./components/login";
import Register from "./components/register";
import AgentDashboard from "./components/agent-dashboard";
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
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
