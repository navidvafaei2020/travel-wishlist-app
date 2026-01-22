import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FaPlane, FaArrowRight } from "react-icons/fa";
import { authAPI } from "../services/authService";
import { destinationAPI } from "../services/destinationService";
import { wishlistAPI } from "../services/wishlistService";

const Home = () => {
  const role = authAPI.getUserRole();

  const [destinations, setDestinations] = useState([]);
  const [wishlists, setWishlists] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!role) return; // Wait until role is ready

    const loadStats = async () => {
      try {
        // Fetch all destinations
        const dests = await destinationAPI.getAll();
        setDestinations(dests);

        // Fetch wishlist items for USER only
        if (role === "USER") {
          const userWishlist = await wishlistAPI.getUserWishlist();
          setWishlists(userWishlist);
        }
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadStats();
  }, [role]);

  return (
    <div className="text-center mt-20 px-4">
      {/* Icon */}
      <FaPlane className="text-6xl text-blue-600 mx-auto mb-4" />

      {/* Title */}
      <h1 className="text-4xl font-bold mb-4">Travel Wishlist</h1>

      {/* Description */}
      <p className="text-gray-600 max-w-xl mx-auto mb-8">
        Discover destinations around the world and create your personal travel wishlist.
      </p>

      {/* Statistics */}
      {!loading && (
        <div className="flex flex-col sm:flex-row justify-center items-center gap-6 mb-8">
          <div className="bg-blue-50 p-6 rounded-lg shadow min-w-[150px]">
            <h3 className="text-lg font-semibold mb-2">Destinations</h3>
            <p className="text-3xl font-bold">{destinations.length}</p>
          </div>

          {role === "USER" && (
            <div className="bg-green-50 p-6 rounded-lg shadow min-w-[150px]">
              <h3 className="text-lg font-semibold mb-2">My Wishlist</h3>
              <p className="text-3xl font-bold">{wishlists.length}</p>
            </div>
          )}
        </div>
      )}

      {/* Role-based CTA */}
      {role === "ADMIN" && (
        <Link
          to="/destinations"
          className="inline-flex items-center gap-2 text-white bg-blue-600 px-6 py-3 rounded-lg font-medium hover:bg-blue-700 transition mb-4"
        >
          See Your Destinations <FaArrowRight />
        </Link>
      )}

      {role === "USER" && (
        <Link
          to="/wishlist"
          className="inline-flex items-center gap-2 text-white bg-green-600 px-6 py-3 rounded-lg font-medium hover:bg-green-700 transition mb-4"
        >
          See My Wishlist <FaArrowRight />
        </Link>
      )}
    </div>
  );
};

export default Home;