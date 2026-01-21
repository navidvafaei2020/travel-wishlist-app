import api from "./api";

export const destinationAPI = {
  getAll: () => api.get("/destinations").then(res => res.data),
  getById: (id) => api.get(`/destinations/${id}`).then(res => res.data),
  create: (data) => api.post("/destinations", data),
  update: (id, data) => api.put(`/destinations/${id}`, data),
  delete: (id) => api.delete(`/destinations/${id}`),
  getByTagsSuggestion: (tags) => api.get(`/destinations/tags/${tags}`).then(res => res.data),  
  clearTags: (id) => api.delete(`/destination-tags/destination/${id}`),
};