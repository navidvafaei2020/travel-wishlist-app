import { Link, useNavigate } from "react-router-dom";
import { authAPI } from "../services/authService";

const Navbar = () => {
  const navigate = useNavigate();
  const isAdmin = authAPI.isAdmin();
  const isLoggedIn = authAPI.getToken();

  const handleLogout = () => {
    authAPI.logout();
    navigate("/login");
  };

  return (
    <nav className="bg-white shadow-md px-6 py-4 flex justify-between items-center">
      {/* Logo */}
      <Link to="/" className="text-2xl font-bold text-blue-600">
        Travel Wishlist
      </Link>

      {/* Links */}
      <div className="flex items-center gap-6">
        {isLoggedIn && isAdmin && (
          <Link
            to="/destinations"
            className="text-gray-700 hover:text-blue-600 font-medium"
          >
            Destinations
          </Link>
        )}

        {isLoggedIn && !isAdmin && (
          <Link
            to="/wishlist"
            className="text-gray-700 hover:text-blue-600 font-medium"
          >
            My Wishlist
          </Link>
        )}

        {isLoggedIn ? (
          <button
            onClick={handleLogout}
            className="bg-red-500 text-white px-4 py-1.5 rounded hover:bg-red-600"
          >
            Logout
          </button>
        ) : (
          <Link
            to="/login"
            className="bg-blue-500 text-white px-4 py-1.5 rounded hover:bg-blue-600"
          >
            Login
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;