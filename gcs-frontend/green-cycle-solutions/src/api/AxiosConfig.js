import axios from "axios";

axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    console.log(token);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      console.log(token);
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axios;
