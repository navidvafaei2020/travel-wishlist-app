import api from "./api";

/**
 * countryAPI module for interacting with the backend countries endpoints.
 * 
 * <p>Provides functions to fetch country-related data.</p>
 * 
 * Functions:
 * <ul>
 *   <li><code>getAll()</code> – Retrieves the list of all countries from the backend and returns the data.</li>
 * </ul>
 * 
 * Uses the shared <code>api</code> instance for HTTP requests.
 */

export const countryAPI = {
  getAll: () => api.get("/countries").then(res => res.data),
};