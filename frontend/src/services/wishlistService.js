import api from "./api";


/**
 * wishlistAPI module for managing the user's travel wishlist.
 * 
 * <p>Provides functions to fetch, add, and remove destinations from a user's wishlist.</p>
 * 
 * Functions:
 * <ul>
 *   <li><code>getUserWishlist()</code> – Retrieves the current user's wishlist from the backend.</li>
 *   <li><code>addDestination(id)</code> – Adds a destination to the user's wishlist by destination ID.</li>
 *   <li><code>removeDestination(id)</code> – Removes a destination from the user's wishlist by destination ID.</li>
 * </ul>
 * 
 * Uses the shared <code>api</code> instance for HTTP requests.
 */

export const wishlistAPI = {
  getUserWishlist: () => api.get("/wishlist").then(res => res.data),
  addDestination: (id) => api.post(`/wishlist/${id}`),
  removeDestination: (id) => api.delete(`/wishlist/${id}`),
};
