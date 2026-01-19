import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { destinationAPI } from "../services/destinationService";
import { wishlistAPI } from "../services/wishlistService";
import { authAPI } from "../services/authService";

const Destinations = () => {
  const [destinations, setDestinations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  const filteredDestinations = destinations.filter(dest =>
  dest.name.toLowerCase().includes(search.toLowerCase()) ||
  dest.countryName?.toLowerCase().includes(search.toLowerCase())
  );



  const navigate = useNavigate();

  const isAdmin = authAPI.isAdmin();
  const token = authAPI.getToken();

  useEffect(() => {
    loadDestinations();
  }, []);

  const loadDestinations = async () => {
    try {
      const data = isAdmin
        ? await destinationAPI.getAll()
        : await destinationAPI.getAllPublic();

      setDestinations(data);
    } catch (error) {
      console.error("Failed to load destinations", error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddToWishlist = async (destinationId) => {
    try {
      await wishlistAPI.add(destinationId);
      alert("Added to wishlist ❤️");
    } catch (error) {
      console.error(error);
      alert("Failed to add to wishlist");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this destination?")) return;
    try {
      await destinationAPI.delete(id);
      setDestinations(destinations.filter(d => d.id !== id));
    } catch (error) {
      console.error(error);
      alert("Delete failed");
    }
  };

  if (loading) {
    return <p className="text-center mt-10">Loading destinations...</p>;
  }

  return (
    <div className="max-w-7xl mx-auto px-6 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Destinations</h1>

        <input
          type="text"
          placeholder="Search destinations or countries..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full max-w-md mb-6 border p-2 rounded"
        />

        {isAdmin && (
          <button
            onClick={() => navigate("/admin/destinations/new")}
            className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
          >
            + Add Destination
          </button>
        )}
      </div>

      {destinations.length === 0 ? (
        <p>No destinations found.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredDestinations.map(dest => (
            <div
              key={dest.id}
              className="bg-white rounded-xl shadow-md overflow-hidden flex flex-col"
            >
              <img
                src={dest.imageUrl ? dest.imageUrl : "/images/default.jpg"}
                alt={dest.name}
                className="h-48 w-full object-cover"
              />
              <div className="p-4 flex flex-col flex-grow">
                <h2 className="text-xl font-semibold">{dest.name} </h2>
                <p className="text-sm text-gray-600 mb-2">
                  {dest.countryName}
                </p>
                <p className="text-gray-700 text-sm flex-grow">
                  {dest.description}
                </p>

                <div className="mt-4 flex gap-2">
                  {!isAdmin && (
                    <button
                      onClick={() => handleAddToWishlist(dest.id)}
                      className="flex-1 bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
                    >
                      Add to Wishlist
                    </button>
                  )}

                  {isAdmin && (
                    <>
                      <button
                        onClick={() =>
                          navigate(`/admin/destinations/edit/${dest.id}`)
                        }
                        className="flex-1 bg-indigo-600 text-white py-2 rounded hover:bg-indigo-700"
                      >
                        Edit
                      </button>

                      <button
                        onClick={() => handleDelete(dest.id)}
                        className="flex-1 bg-indigo-600 text-white py-2 rounded hover:bg-indigo-700"
                      >
                        Delete
                      </button>
                    </>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Destinations;