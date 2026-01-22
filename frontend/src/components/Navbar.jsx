import { Link, useNavigate } from "react-router-dom";
import { FaHome, FaInfoCircle, FaUserCircle, FaSignOutAlt } from "react-icons/fa";
import { authAPI } from "../services/authService";

const Navbar = () => {
  const navigate = useNavigate();
  const role = authAPI.getUserRole();
  const token = authAPI.getToken();

  const handleLogout = () => {
    authAPI.logout();
    navigate("/login");
  };

  return (
    <nav className="bg-blue-600 text-white px-6 py-3 flex justify-between items-center shadow-md">
      {/* Left side */}
      <div className="flex items-center gap-6">
        <Link to="/" className="flex items-center gap-1 hover:text-gray-200">
          <FaHome />
          Home
        </Link>

        <Link to="/about" className="flex items-center gap-1 hover:text-gray-200">
          <FaInfoCircle />
          About
        </Link>

        {role === "ADMIN" && (
          <Link
            to="/destinations"
            className="hover:text-gray-200"
          >
            Destinations
          </Link>
        )}

        {role === "ADMIN" && (
          <li>
            <Link to="/admin/tags" className="hover:text-purple-600">
              Manage Tags
            </Link>
          </li>
        )}

        {role === "USER" && (
          <Link
            to="/wishlist"
            className="hover:text-gray-200"
          >
            My Wishlist
          </Link>
        )}
      </div>

      {/* Right side */}
      <div className="flex items-center gap-4">
        {token ? (
          <>
            <div className="flex items-center gap-1">
              <FaUserCircle />
              <span className="font-medium">
                {authAPI.getUsername()}
              </span>
            </div>

            <button
              onClick={handleLogout}
              className="flex items-center gap-1 hover:text-gray-200"
            >
              <FaSignOutAlt />
              Logout
            </button>
          </>
        ) : (
          <Link to="/login" className="hover:text-gray-200">
            Login
          </Link>
        )}
      </div>
    </nav>
  );
};

export default Navbar;