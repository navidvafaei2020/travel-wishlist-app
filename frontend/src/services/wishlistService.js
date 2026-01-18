import { api } from "./api";

export const wishlistAPI = {
  getUserWishlist: () => api.get("/wishlists/user").then(res => res.data),
  addDestination: (destinationId) =>
    api.post("/wishlists", { destinationId }).then(res => res.data),
  removeDestination: (wishlistId) =>
    api.delete(`/wishlists/${wishlistId}`).then(res => res.data),
};