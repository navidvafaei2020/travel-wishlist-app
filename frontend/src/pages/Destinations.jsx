import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { destinationAPI } from "../services/destinationService";
import { wishlistAPI } from "../services/wishlistService";
import { authAPI } from "../services/authService";
import TagChip from "../components/TagChip";


const Destinations = () => {
  const [destinations, setDestinations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [suggestInput, setSuggestInput] = useState("");
  const [suggestedNames, setSuggestedNames] = useState(null);  

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
/*
  const getSuggestedDestinations = (list) => {
  if (!suggestInput.trim()) return list;

  const keywords = suggestInput
    .toLowerCase()
    .split(",")
    .map(k => k.trim())
    .filter(Boolean);

  return list.filter(dest =>
    dest.tags?.some(tag =>
      keywords.some(keyword =>
        tag.toLowerCase().includes(keyword)
      )
    )
  );
  };



const filteredDestinations = destinations.filter(dest =>
    dest.name.toLowerCase().includes(search.toLowerCase()) ||
    dest.countryName?.toLowerCase().includes(search.toLowerCase())
  );
*/

/*
const suggestedDestinations = getSuggestedDestinations(destinations);

const filteredDestinations = suggestedDestinations.filter(dest =>
  dest.name.toLowerCase().includes(search.toLowerCase()) ||
  dest.countryName?.toLowerCase().includes(search.toLowerCase())
);



const filteredDestinations = destinations.filter(dest => {
  const q = search.toLowerCase();

  return (
    dest.name.toLowerCase().includes(q) ||
    dest.description?.toLowerCase().includes(q) ||
    dest.countryName?.toLowerCase().includes(q) ||
    dest.tags?.some(tag => tag.toLowerCase().includes(q))
  );
  });


  */


  const filteredDestinations = destinations.filter(dest => {
    if (suggestedNames) {
      return suggestedNames.includes(dest.name.toLowerCase());
    }
    const q = search.toLowerCase();
    return (
      dest.name.toLowerCase().includes(q) ||
      dest.description?.toLowerCase().includes(q) ||
      dest.countryName?.toLowerCase().includes(q) ||
      dest.tags?.some(tag => tag.toLowerCase().includes(q))
    );
  });


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


  const handleSuggestMe = async () => {
    if (!suggestInput.trim()) return;

    try {
      const result = await destinationAPI.getByTagsSuggestion(suggestInput);
      const names = result.split(",").map(n => n.trim());
      setSuggestedNames(names);
    } catch (err) {
      console.error("Suggest failed", err);
    }
  };


  const handleResetSuggestions = () => {
    setSuggestedNames(null);
    setSuggestInput("");
  };
  
  if (loading) {
    return <p className="text-center mt-10">Loading destinations...</p>;
  }


  const clearTags = async (id) => {
  if (!window.confirm("Are you sure you want to remove all tags from this destination?")) {
    return;
  }

  try {
    await destinationAPI.clearTags(id);

    // update UI immediately
    setDestinations(prev =>
      prev.map(d =>
        d.id === id ? { ...d, tags: [] } : d
      )
    );
  } catch (err) {
    console.error(err);
    alert("Failed to clear tags");
  }
};


return (
  <div className="max-w-7xl mx-auto px-6 py-8">
    {/* Header + Search */}
    <div className="flex flex-wrap justify-between items-center gap-4 mb-6">
      <h1 className="text-3xl font-bold">Destinations</h1>

      <input
        type="text"
        placeholder="Search destinations..."
        value={search}
        onChange={(e) => {
          setSearch(e.target.value);
          setSuggestedNames(null);
        }}
        className="border p-2 rounded w-full sm:w-80"
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

    {/* Suggest Me Frame */}
    <div className="bg-purple-50 border border-purple-200 rounded-xl p-5 mb-8 shadow-sm">
      <h2 className="text-lg font-semibold mb-3 flex items-center gap-2">
        ✨ Smart Travel Assistant
      </h2>

      <div className="flex flex-wrap gap-3 items-center">
        <input
          type="text"
          placeholder="cheap, beach, luxury"
          value={suggestInput}
          onChange={(e) => setSuggestInput(e.target.value)}
          className="flex-1 min-w-[220px] border p-2 rounded"
        />

        <button
          onClick={handleSuggestMe}
          disabled={!suggestInput.trim()}
          className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700 disabled:bg-purple-300"
        >
          Suggest Me
        </button>

        {suggestedNames && (
          <button
            onClick={handleResetSuggestions}
            className="bg-gray-200 text-gray-700 px-4 py-2 rounded hover:bg-gray-300"
          >
            Reset
          </button>
        )}
      </div>

      {/* Suggestions Result (same frame, one line) */}
      {suggestedNames && (
        <div className="mt-4 flex flex-wrap gap-3 items-center">
          <span className="text-sm text-gray-600 font-medium">
            Results:
          </span>

          {suggestedNames.slice(0, 10).map((name, index) => (
            <span
              key={index}
              className="px-4 py-1 rounded-full bg-indigo-100 text-indigo-700 text-sm font-medium"
            >
              {name}
            </span>
          ))}
        </div>
      )}
    </div>



    {/* Destination Grid */}
    {filteredDestinations.length === 0 ? (
      <p className="text-center text-gray-500">No destinations found.</p>
    ) : (
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {filteredDestinations.map(dest => (
          <div
            key={dest.id}
            className="bg-white rounded-xl shadow-md overflow-hidden flex flex-col"
          >
            <img
              src={dest.imageUrl || "/images/default.jpg"}
              alt={dest.name}
              className="h-48 w-full object-cover"
            />

            <div className="p-4 flex flex-col flex-grow">
              <h2 className="text-xl font-semibold">{dest.name}</h2>
              <p className="text-sm text-gray-600 mb-2">
                {dest.countryName}
              </p>

              <p className="text-gray-700 text-sm flex-grow">
                {dest.description}
              </p>

              {/* Tags */}
              {dest.tags?.length > 0 && (
                <div className="flex flex-wrap gap-2 mt-3">
                  {dest.tags.map((tag, idx) => (
                    <TagChip key={idx} label={tag} />
                  ))}
                </div>
              )}

              {/* Actions */}
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
  <div className="mt-4 flex flex-wrap gap-2">

  <div className="mt-4 flex flex-wrap gap-2">
    <button
      onClick={() => navigate(`/admin/destinations/edit/${dest.id}`)}
      className="bg-indigo-100 text-indigo-800 px-4 py-2 rounded hover:bg-indigo-200"
    >
      Edit
    </button>

    <button
      onClick={() => handleDelete(dest.id)}
      className="bg-indigo-100 text-indigo-800 px-4 py-2 rounded hover:bg-indigo-200"
    >
      Delete
    </button>

    <button
      onClick={() => clearTags(dest.id)}
      className="bg-indigo-100 text-indigo-800 px-4 py-2 rounded hover:bg-indigo-200"
    >
      Clear Tags
    </button>
  </div>
                  </div>

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