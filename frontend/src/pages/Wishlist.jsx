import { useEffect, useState } from "react";
import { wishlistAPI } from "../services/wishlistService";
import { destinationAPI } from "../services/destinationService";
import { authAPI } from "../services/authService";

const Wishlist = () => {
  const [destinations, setDestinations] = useState([]);
  const [wishlistIds, setWishlistIds] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (authAPI.isAdmin()) return;

    const loadData = async () => {
      try {
        const allDestinations = await destinationAPI.getAll();
        const wishlist = await wishlistAPI.getUserWishlist();
        
        setDestinations(allDestinations);
        setWishlistIds(wishlist.map(w => w.destinationId));
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const addToWishlist = async (id) => {
    await wishlistAPI.addDestination(id);
    setWishlistIds(prev => [...prev, id]);
  };

  const removeFromWishlist = async (id) => {
    await wishlistAPI.removeDestination(id);
    setWishlistIds(prev => prev.filter(w => w !== id));
  };

  if (loading) return <p className="text-center mt-10">Loading...</p>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold text-center mb-8">My Travel Wishlist</h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {destinations.map(dest => {
          const inWishlist = wishlistIds.includes(dest.id);

          return (
            <div
              key={dest.id}
              className="bg-white rounded-xl shadow-lg overflow-hidden flex flex-col"
            >
              <img
                src={dest.imageUrl}
                alt={dest.name}
                className="h-44 w-full object-cover"
              />

              <div className="p-4 flex flex-col flex-grow">
                <h2 className="text-xl font-semibold mb-2">{dest.name}</h2>
                <p className="text-gray-600 flex-grow">
                  {dest.description}
                </p>

                {!inWishlist ? (
                  <button
                    onClick={() => addToWishlist(dest.id)}
                    className="mt-4 bg-blue-500 text-white py-2 rounded hover:bg-blue-600"
                  >
                    ➕ Add to Wishlist
                  </button>
                ) : (
                  <button
                    onClick={() => removeFromWishlist(dest.id)}
                    className="mt-4 bg-red-500 text-white py-2 rounded hover:bg-red-600"
                  >
                    ❌ Remove from Wishlist
                  </button>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Wishlist;