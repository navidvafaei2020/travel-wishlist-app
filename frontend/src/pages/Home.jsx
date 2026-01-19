import { Link } from "react-router-dom";
import { FaPlane, FaArrowRight } from "react-icons/fa";
import { authAPI } from "../services/authService";

const Home = () => {
  const role = authAPI.getUserRole();

  return (
    <div className="text-center mt-20">
      <FaPlane className="text-6xl text-blue-600 mx-auto mb-4" />

      <h1 className="text-4xl font-bold mb-4">
        Travel Wishlist
      </h1>

      <p className="text-gray-600 max-w-xl mx-auto mb-8">
        Discover destinations around the world and create your personal travel wishlist.
      </p>

      {/* Role-based CTA */}
      {role === "ADMIN" && (
        <Link
          to="/destinations"
          className="inline-flex items-center gap-2 text-blue-600 font-medium hover:underline"
        >
          See your destinations <FaArrowRight />
        </Link>
      )}

      {role === "USER" && (
        <Link
          to="/wishlist"
          className="inline-flex items-center gap-2 text-green-600 font-medium hover:underline"
        >
          See your wishlists <FaArrowRight />
        </Link>
      )}
    </div>
  );
};

export default Home;