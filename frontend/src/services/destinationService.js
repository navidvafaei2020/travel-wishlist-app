import api from "./api";

/**
 * destinationAPI module for interacting with the backend destinations endpoints.
 * 
 * <p>Provides functions to perform CRUD operations, manage tags, and fetch suggestions for destinations.</p>
 * 
 * Functions:
 * <ul>
 *   <li><code>getAll()</code> – Retrieves all destinations.</li>
 *   <li><code>getById(id)</code> – Retrieves a single destination by its ID.</li>
 *   <li><code>create(data)</code> – Creates a new destination with the provided data.</li>
 *   <li><code>update(id, data)</code> – Updates an existing destination by ID.</li>
 *   <li><code>delete(id)</code> – Deletes a destination by ID.</li>
 *   <li><code>getByTagsSuggestion(tags)</code> – Fetches destination names based on tag suggestions.</li>
 *   <li><code>getAllTags()</code> – Retrieves all available tags.</li>
 *   <li><code>clearTags(destinationId)</code> – Deletes all tags associated with a specific destination.</li>
 *   <li><code>addAllTags(destinationId, tagIds)</code> – Adds multiple tags to a destination; accepts an array of tag IDs.</li>
 * </ul>
 * 
 * Uses the shared <code>api</code> instance for HTTP requests.
 */

export const destinationAPI = {
  getAll: () => api.get("/destinations").then(res => res.data),
  getById: (id) => api.get(`/destinations/${id}`).then(res => res.data),
  create: (data) => api.post("/destinations", data),
  update: (id, data) => api.put(`/destinations/${id}`, data),
  delete: (id) => api.delete(`/destinations/${id}`),

  getByTagsSuggestion: (tags) =>
    api.get(`/destinations/tags/${tags}`).then(res => res.data),

  getAllTags: () => api.get("/tags").then(res => res.data),

  clearTags: (destinationId) =>
    api.delete(`/destination-tags/destination/${destinationId}`),

  addAllTags: (destinationId, tagIds) =>
    api.post(`/destination-tags/destination/${destinationId}`, tagIds), // raw array [1,2,3]
};