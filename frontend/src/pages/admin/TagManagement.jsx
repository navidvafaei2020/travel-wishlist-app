import { useEffect, useState } from "react";
import { tagAPI } from "../../services/tagService";
import { authAPI } from "../../services/authService";
import { useNavigate } from "react-router-dom";

const TagManagement = () => {
  const navigate = useNavigate();
  const [tags, setTags] = useState([]);
  const [newTagName, setNewTagName] = useState("");
  const [editingTag, setEditingTag] = useState(null); // {id, name}
  const [errorMessage, setErrorMessage] = useState("");


  useEffect(() => {
    if (!authAPI.isAdmin()) navigate("/login");

    const loadTags = async () => {
      try {
        const allTags = await tagAPI.getAll();
        setTags(allTags);
      } catch (err) {
        console.error(err);
      }
    };

    loadTags();
  }, [navigate]);

  const handleAddTag = async () => {
    if (!newTagName.trim()) return;

    try {
      const created = await tagAPI.create({ name: newTagName });
      setTags(prev => [...prev, created]);
      setNewTagName("");
      setErrorMessage(""); // clear error
    } catch (err) {
      console.error(err.response || err);
      if (err.response?.status === 409) {
        setErrorMessage(err.response.data.message);
      } else {
        setErrorMessage("Failed to add tag. Try again.");
      }
    }
  };

  const handleUpdateTag = async () => {
    if (!editingTag || !newTagName.trim()) return;

    try {
      const updated = await tagAPI.update(editingTag.id, { name: newTagName });
      setTags(prev => prev.map(t => t.id === updated.id ? updated : t));
      setEditingTag(null);
      setNewTagName("");
      setErrorMessage(""); // clear error
    } catch (err) {
      console.error(err.response || err);
      if (err.response?.status === 409) {
        setErrorMessage(err.response.data.message);
      } else {
        setErrorMessage("Failed to update tag. Try again.");
      }
    }
  };


// Edit tag: populate form
const handleEditTag = (tag) => {
  setEditingTag(tag);
  setNewTagName(tag.name);
  setErrorMessage("");
};  


  const handleDeleteTag = async (id) => {
    if (!window.confirm("Delete this tag?")) return;

    try {
      await tagAPI.delete(id);
      setTags(prev => prev.filter(t => t.id !== id));
    } catch (err) {
      console.error(err);
    }
  };



return (
  <div className="max-w-xl mx-auto mt-10 p-6 bg-white rounded-lg shadow-lg">

    {/* Header + Back button */}
    <div className="flex justify-between items-center mb-6">
      <h2 className="text-2xl font-bold">Manage Tags</h2>
      <button
        onClick={() => navigate(-1)}
        className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
      >
        Back
      </button>
    </div>

    {errorMessage && (
      <div className="mb-4 text-red-600 font-medium">{errorMessage}</div>
    )}

    {/* Add / Edit Form */}
    <div className="flex gap-2 mb-6">
      <input
        type="text"
        placeholder="Tag name"
        value={newTagName}
        onChange={(e) => setNewTagName(e.target.value)}
        className="flex-1 border p-2 rounded"
      />
      {editingTag ? (
        <button
          onClick={handleUpdateTag}
          className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Update
        </button>
      ) : (
        <button
          onClick={handleAddTag}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          Add
        </button>
      )}
      {editingTag && (
        <button
          onClick={() => {
            setEditingTag(null);
            setNewTagName("");
          }}
          className="bg-gray-300 text-gray-800 px-4 py-2 rounded hover:bg-gray-400"
        >
          Cancel
        </button>
      )}
    </div>


      {/* Tag List */}
      <div className="space-y-2">
        {tags.map(tag => (
          <div
            key={tag.id}
            className="flex justify-between items-center border p-2 rounded"
          >
            <span className="text-gray-800">{tag.name}</span>

            <div className="flex gap-2">              
              <button
                onClick={() => handleEditTag(tag)}
                className="bg-yellow-400 text-white px-3 py-1 rounded hover:bg-yellow-500 text-xs"
              >
                ✏️
              </button>
              <button
                onClick={() => handleDeleteTag(tag.id)}
                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 text-xs"
              >
                🗑️
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TagManagement;