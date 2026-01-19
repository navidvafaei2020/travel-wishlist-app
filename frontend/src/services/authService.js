import api from "./api";


// decode JWT to get payload
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
