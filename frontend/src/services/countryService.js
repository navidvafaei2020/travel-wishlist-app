import api from "./api";

export const countryAPI = {
  getAll: () => api.get("/countries").then(res => res.data),
};