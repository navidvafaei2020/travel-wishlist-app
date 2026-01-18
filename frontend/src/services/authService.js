import { api } from "./api";

export const authAPI = {
  login: async (credentials) => {
    const { data } = await api.post("/auth/login", credentials);
    sessionStorage.setItem("token", data.token);
    sessionStorage.setItem("user", JSON.stringify(data.user));
    return data;
  },

  logout: () => {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("user");
  },

  getToken: () => sessionStorage.getItem("token"),
  getUser: () => JSON.parse(sessionStorage.getItem("user")),
  isAdmin: () => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    return user?.role === "ADMIN";
  },
};