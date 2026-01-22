import api from "./api";

/**
 * tagAPI module for interacting with the backend tags endpoints.
 * 
 * <p>Provides functions to manage tags in the Travel Wishlist application.</p>
 * 
 * Functions:
 * <ul>
 *   <li><code>getAll()</code> – Retrieves all tags from the backend.</li>
 *   <li><code>create(data)</code> – Creates a new tag with the provided data and returns the created tag.</li>
 *   <li><code>update(id, data)</code> – Updates an existing tag by ID and returns the updated tag.</li>
 *   <li><code>delete(id)</code> – Deletes a tag by ID.</li>
 * </ul>
 * 
 * Uses the shared <code>api</code> instance for HTTP requests.
 */

export const tagAPI = {
  getAll: () => api.get("/tags").then(res => res.data),
  create: (data) => api.post("/tags", data).then(res => res.data),
  update: (id, data) => api.put(`/tags/${id}`, data).then(res => res.data),
  delete: (id) => api.delete(`/tags/${id}`),
};