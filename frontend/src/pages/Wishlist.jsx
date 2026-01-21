import { useEffect, useState } from "react";
import { wishlistAPI } from "../services/wishlistService";
import { destinationAPI } from "../services/destinationService";
import { authAPI } from "../services/authService";
import TagChip from "../components/TagChip";


const Wishlist = () => {
  const [destinations, setDestinations] = useState([]);
  const [wishlistIds, setWishlistIds] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [suggestInput, setSuggestInput] = useState("");
  const [suggestedNames, setSuggestedNames] = useState(null);



  useEffect(() => {
    const role = authAPI.getUserRole();
    if (!role) return;           // token not ready yet
    if (role !== "USER") return; // only USER can see wishlist

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



const handleSuggestMe = async () => {
  if (!suggestInput.trim()) return;

  try {
    const result = await destinationAPI.getByTagsSuggestion(suggestInput);
    // "paris, frankfurt" → ["paris", "frankfurt"]
    const names = result
      .split(",")
      .map(n => n.trim().toLowerCase());

    setSuggestedNames(names);
  } catch (err) {
    console.error(err);
  }
};


const handleResetSuggestions = () => {
  setSuggestedNames(null);
  setSuggestInput("");
  // optional:
  // setSearch("");
};




  if (loading) return <p className="text-center mt-10">Loading...</p>;


  /*

  const filteredDestinations = sortedDestinations.filter(dest =>
    dest.name.toLowerCase().includes(search.toLowerCase())
  );
  */


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
*/





  const sortedDestinations = [...destinations].sort((a, b) => {
  const aSelected = wishlistIds.includes(a.id);
  const bSelected = wishlistIds.includes(b.id);

  if (aSelected && !bSelected) return -1;
  if (!aSelected && bSelected) return 1;
  return 0;
  });

/*
  const suggestedDestinations = getSuggestedDestinations(sortedDestinations);
  const filteredDestinations = suggestedDestinations.filter(dest =>
    dest.name.toLowerCase().includes(search.toLowerCase())
  );



const filteredDestinations = sortedDestinations.filter(dest => {
  const q = search.toLowerCase();

  
  if (suggestedNames) {
    return suggestedNames.includes(dest.name.toLowerCase());
  }


  return (
    dest.name.toLowerCase().includes(q) ||
    dest.description?.toLowerCase().includes(q) ||
    dest.countryName?.toLowerCase().includes(q) ||
    dest.tags?.some(tag => tag.toLowerCase().includes(q))
  );
});

*/

const filteredDestinations = sortedDestinations.filter(dest => {
  // If backend suggestion is active
  if (suggestedNames) {
    return suggestedNames.includes(dest.name.toLowerCase());
  }

  // Normal search
  const q = search.toLowerCase();
  return (
    dest.name.toLowerCase().includes(q) ||
    dest.description?.toLowerCase().includes(q) ||
    dest.countryName?.toLowerCase().includes(q) ||
    dest.tags?.some(tag => tag.toLowerCase().includes(q))
  );
});


/*

  const filteredDestinations = sortedDestinations.filter(dest => {
  const q = search.toLowerCase();

  return (
    dest.name.toLowerCase().includes(q) ||
    dest.description?.toLowerCase().includes(q) ||
    dest.countryName?.toLowerCase().includes(q) ||
    dest.tags?.some(tag => tag.toLowerCase().includes(q))
  );
  });


  */

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold text-center mb-8">My Travel Wishlist</h1>

      <div className="flex gap-2 mb-6 max-w-xl mx-auto">
        {/* Normal search */}
        <input
          type="text"
          placeholder="Search destinations..."
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
            setSuggestedNames(null);
          }}
          className="flex-1 border p-2 rounded"
        />

        {/* Suggest Me input */}
        <input
          type="text"
          placeholder="e.g. cheap, luxury"
          value={suggestInput}
          onChange={(e) => setSuggestInput(e.target.value)}
          className="flex-1 border p-2 rounded"
        />

        {/* Suggest button */}      
        <button
          onClick={handleSuggestMe}
          disabled={!suggestInput.trim()}
          className="bg-purple-600 text-white px-4 rounded hover:bg-purple-700 disabled:bg-purple-300"
        >
          Suggest
        </button>

        {/* Reset button */}
        {suggestedNames && (
          <button
            onClick={handleResetSuggestions}
            className="bg-purple-600 text-white px-4 rounded hover:bg-purple-700"
          >
            Reset
          </button>
        )}
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {filteredDestinations.map(dest => {
          const inWishlist = wishlistIds.includes(dest.id);

          return (
            <div
              key={dest.id}
              className="bg-white rounded-xl shadow-lg overflow-hidden flex flex-col"
            >
              <img
                src={dest.imageUrl || "/images/default.jpg"}
                alt={dest.name}
                className="h-44 w-full object-cover"
              />

              <div className="p-4 flex flex-col flex-grow">
                <h2 className="text-xl font-semibold mb-2">{dest.name}</h2>
                <p className="text-gray-600 flex-grow">
                  {dest.description}
                </p>

                {dest.tags && dest.tags.length > 0 && (
                  <div className="flex flex-wrap gap-2 mt-3">
                    {dest.tags.map((tag, index) => (
                      <TagChip key={index} label={tag} />
                    ))}
                  </div>
                )}                

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