import { createContext, useState, useEffect } from "react";

const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState(() => {
    // get data from localStorage if available and initialize auth
    const storedAuth = localStorage.getItem("user");
    return storedAuth
      ? {
          usernameContext: JSON.parse(storedAuth).usernameContext,
          passwordContext: JSON.parse(storedAuth).passwordContext,
          role: JSON.parse(storedAuth).role,
          gender: JSON.parse(storedAuth).gender,
        }
      : {
          usernameContext: null,
          passwordContext: null,
          role: null,
          gender: null,
        };
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
