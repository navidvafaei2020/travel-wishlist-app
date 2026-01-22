import api from "./api";

export const tagAPI = {
  getAll: () => api.get("/tags").then(res => res.data),
  create: (data) => api.post("/tags", data).then(res => res.data),
  update: (id, data) => api.put(`/tags/${id}`, data).then(res => res.data),
  delete: (id) => api.delete(`/tags/${id}`),
};