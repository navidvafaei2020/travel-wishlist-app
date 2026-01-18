import { api } from "./api";

export const destinationAPI = {
  getAll: () => api.get("/destinations").then(res => res.data),
  getById: (id) => api.get(`/destinations/${id}`).then(res => res.data),
  create: (data) => api.post("/destinations", data).then(res => res.data),
};