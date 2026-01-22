import axios from "axios";

/**
 * Axios instance for communicating with the backend API.
 * 
 * <p>This instance sets the base URL for all requests and automatically
 * attaches an Authorization header if a JWT token exists in sessionStorage.</p>
 * 
 * Example usage:
 * <pre>
 *   import api from './api';
 *   const response = await api.get('/tags');
 * </pre>
 */

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

api.interceptors.request.use((config) => {
  const token = sessionStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;