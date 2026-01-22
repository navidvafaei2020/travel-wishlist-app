import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { authAPI } from "../services/authService";

/**
 * Register component for the Travel Wishlist application.
 * 
 * <p>This component provides a user registration form with the following features:</p>
 * <ul>
 *   <li>Form fields for first name, last name, email, username, password, and role selection.</li>
 *   <li>Client-side state management for form input and validation errors.</li>
 *   <li>Submits registration data via <code>authAPI.register</code> and stores the returned token.</li>
 *   <li>Handles field-level validation errors and general API errors.</li>
 *   <li>Redirects users based on role after successful registration:
 *     <ul>
 *       <li>ADMIN → /destinations</li>
 *       <li>USER → /wishlist</li>
 *     </ul>
 *   </li>
 *   <li>Provides a link to the login page for existing users.</li>
 * </ul>
 * 
 * Uses TailwindCSS for styling and responsive layout.
 * 
 * @component
 */


  const Register = () => {
  const navigate = useNavigate();

  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState("");


  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    username: "",
    password: "",
    role: "USER", // default
  });


  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    // Clear field error
    if (errors[e.target.name]) {
      setErrors({ ...errors, [e.target.name]: "" });
    }
    setGeneralError("");
  };

 
  const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const data = await authAPI.register(form);

    // store token (same as login)
    if (data.token) {
      sessionStorage.setItem("token", data.token);
    }

    // redirect by role (from response OR JWT)
    if (data.role === "ADMIN") {
      navigate("/destinations");
    } else {
      navigate("/wishlist");
    }

  } catch (err) {
      // We have different types of error structure
        console.error(err);

        const data = err.response?.data;

        // Reset previous errors
        setErrors({});
        setGeneralError("");

        if (data?.errors && typeof data.errors === "object") {
          // Field validation errors
          setErrors(data.errors);
          setGeneralError(data.message || "Validation failed");
        } else if (data?.message) {
          // Business / conflict error
          setGeneralError(data.message);
        } else {
          // Fallback
          setGeneralError("Registration failed");
        }
  }
  };


  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-bold mb-6 text-center">
          Create Account
        </h2>

        {generalError && (
          <div className="bg-red-100 text-red-700 p-2 rounded mb-4 text-sm">
            {generalError}
          </div>
        )}        

        <form onSubmit={handleSubmit} className="space-y-4">
          <label>First Name:</label>
          <input
            name="firstName"
            placeholder="First Name"
            onChange={handleChange}
            //required
            className="w-full border p-2 rounded"
          />

          <label>last Name:</label>
          <input
            name="lastName"
            placeholder="Last Name"
            onChange={handleChange}
            //required
            className="w-full border p-2 rounded"
          />

          <label>Email:</label>
          <input
            type="email"
            name="email"
            placeholder="Email"
            onChange={handleChange}
            required
            className="w-full border p-2 rounded"
          />

          <label>Username:</label>
          <input
            name="username"
            placeholder="Username"
            onChange={handleChange}
            required
            className="w-full border p-2 rounded"
          />
          {errors.username&& (
            <p className="text-red-600 text-sm mt-1">
              {errors.use}
            </p>
          )}          
                    
          <label>Password:</label>
          <input
            type="password"
            name="password"
            placeholder="Password"
            onChange={handleChange}
            required
            className="w-full border p-2 rounded"
          />
          {errors.password && (
            <p className="text-red-600 text-sm mt-1">
              {errors.password}
            </p>
          )}
          
          <label>Role:</label>
          <select
            name="role"
            value={form.role}
            onChange={handleChange}
            className="w-full border p-2 rounded bg-white"
            required
          >
            <option value="USER">User</option>
            <option value="ADMIN">Admin</option>
          </select>

          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
          >
            Register
          </button>
        </form>

        <p className="text-center text-sm mt-4">
          Already have an account?{" "}
          <span
            onClick={() => navigate("/login")}
            className="text-blue-600 cursor-pointer hover:underline"
          >
            Login
          </span>
        </p>
      </div>
    </div>
  );
};

export default Register;