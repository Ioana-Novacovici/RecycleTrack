import axios from "axios";

export const addressesClient = axios.create({
  baseURL: "http://localhost:8080/addresses",
});
