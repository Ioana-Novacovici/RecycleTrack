import { createContext, useState, useEffect } from "react";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(() => {
    // Initialize state with data from localStorage if available
    const storedAuth = localStorage.getItem("user");
    return storedAuth
      ? {
          user: JSON.parse(storedAuth).user,
          role: JSON.parse(storedAuth).role,
        }
      : { user: null, role: null };
  });

  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(auth));
  }, [auth]);

  return (
    <AuthContext.Provider value={{ auth, setAuth }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthContext;
