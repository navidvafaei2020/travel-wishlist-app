import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { authAPI } from "../services/authService";

/**
 * Login component for the Travel Wishlist application.
 * 
 * <p>This component provides a login form for users and admins:</p>
 * <ul>
 *   <li>Inputs for username and password with validation.</li>
 *   <li>Displays error messages on failed login attempts.</li>
 *   <li>Submits credentials via <code>authAPI.login</code> and stores authentication state.</li>
 *   <li>Redirects users based on role:
 *     <ul>
 *       <li>ADMIN → /destinations</li>
 *       <li>USER → /wishlist</li>
 *     </ul>
 *   </li>
 *   <li>Provides a link to the registration page for new users.</li>
 * </ul>
 * 
 * Uses TailwindCSS for styling and responsive layout.
 * 
 * @component
 */


const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();


  const handleSubmit = async (e) => {
  e.preventDefault();
  setError("");
  try {
      const data = await authAPI.login({ username, password });
       // Redirect based on role
      if (authAPI.isAdmin()) {
      navigate("/destinations"); // Admin sees all destinations
      } else {
      navigate("/wishlist"); // User sees their wishlist
      }
  } catch (err) {
      setError("Invalid username or password");
      console.error(err);
  }
  };  

return (
  <div className="flex justify-center items-center min-h-screen bg-gray-100 px-4">
    <form
      onSubmit={handleSubmit}
      className="bg-white p-10 rounded-xl shadow-xl w-full max-w-md"
    >
      <h2 className="text-3xl font-bold mb-6 text-center text-gray-800">
        Login
      </h2>

      {error && (
        <p className="text-red-500 mb-4 text-center font-medium">{error}</p>
      )}

      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="w-full p-3 border border-gray-300 rounded mb-4 focus:ring-2 focus:ring-blue-400 focus:outline-none"
        required
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="w-full p-3 border border-gray-300 rounded mb-6 focus:ring-2 focus:ring-blue-400 focus:outline-none"
        required
      />

      <button
        type="submit"
        className="w-full bg-blue-600 text-white p-3 rounded-lg hover:bg-blue-700 transition"
      >
        Login
      </button>

      <p className="text-center text-sm mt-6 text-gray-600">
        Don&apos;t have an account?{" "}
        <span
          onClick={() => navigate("/register")}
          className="text-blue-600 cursor-pointer hover:underline"
        >
          Register here
        </span>
      </p>
    </form>
  </div>
);
};

export default Login;