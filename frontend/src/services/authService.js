import api from "./api";


/**
 * authAPI module for managing authentication in the Travel Wishlist application.
 * 
 * <p>Provides helper functions for login, registration, token handling, and role management.</p>
 * 
 * Functions:
 * <ul>
 *   <li><code>login(credentials)</code> – Sends credentials to the backend, stores JWT on success, and returns user data.</li>
 *   <li><code>register(userData)</code> – Registers a new user and returns the server response.</li>
 *   <li><code>logout()</code> – Clears the stored JWT from sessionStorage.</li>
 *   <li><code>getToken()</code> – Retrieves the current JWT from sessionStorage.</li>
 *   <li><code>getUserRole()</code> – Decodes the JWT to extract the user's role.</li>
 *   <li><code>getUsername()</code> – Decodes the JWT to retrieve the username.</li>
 *   <li><code>isAdmin()</code> – Returns <code>true</code> if the user's role is ADMIN, else <code>false</code>.</li>
 * </ul>
 * 
 * Internally uses <code>parseJwt</code> to decode JWT payloads.
 */

const parseJwt = (token) => {
  if (!token) return null;
  try {
    const base64Payload = token.split(".")[1];
    const payload = atob(base64Payload);
    return JSON.parse(payload);
  } catch (err) {
    console.error("Failed to decode JWT:", err);
    return null;
  }
};

export const authAPI = {
  login: async (credentials) => {
    const { data } = await api.post("/auth/login", credentials);

    if (data.token) sessionStorage.setItem("token", data.token);

    return data;
  },

  register: (userData) =>
  api.post("/auth/register", userData).then(res => res.data),


  logout: () => {
    sessionStorage.removeItem("token");
  },

  getToken: () => sessionStorage.getItem("token"),

  getUserRole: () => {
    const token = sessionStorage.getItem("token");
    const payload = parseJwt(token);
    return payload?.role || null; // read role from JWT
  },

  getUsername: () => {
  const token = sessionStorage.getItem("token");
  const payload = parseJwt(token);
  return payload?.sub || payload?.username || null;
  },

  isAdmin: () => authAPI.getUserRole() === "ADMIN",
};
