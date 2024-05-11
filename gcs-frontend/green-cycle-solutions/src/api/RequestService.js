import axios from "axios";

export const addressesClient = axios.create({
  baseURL: "http://localhost:8080/addresses",
});

export const collectionsClient = axios.create({
  baseURL: "http://localhost:8080/collections",
});
