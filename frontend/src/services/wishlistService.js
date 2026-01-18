import api from "./api";


export const wishlistAPI = {
  getUserWishlist: () => api.get("/wishlist").then(res => res.data),
  addDestination: (id) => api.post(`/wishlist/${id}`),
  removeDestination: (id) => api.delete(`/wishlist/${id}`),
};
