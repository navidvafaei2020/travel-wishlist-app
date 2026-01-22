import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { destinationAPI } from "../../services/destinationService";
import { countryAPI } from "../../services/countryService";
import { authAPI } from "../../services/authService";

const DestinationForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEdit = Boolean(id);

  const [countries, setCountries] = useState([]);
  const [tags, setTags] = useState([]);
  const [selectedTags, setSelectedTags] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

  const [form, setForm] = useState({
    name: "",
    description: "",
    imageUrl: "/images/default.jpg",
    countryId: "",
  });
  const [selectedFile, setSelectedFile] = useState(null);

  useEffect(() => {
    if (!authAPI.isAdmin()) {
      navigate("/login");
      return;
    }

    const loadFormData = async () => {
      try {
        // 1- Load countries & tags first
        const [countriesData, tagsData] = await Promise.all([
          countryAPI.getAll(),
          destinationAPI.getAllTags(),
        ]);

        setCountries(countriesData);
        setTags(tagsData);


        // 2- If edit → load destination
        if (isEdit) {
          const dest = await destinationAPI.getById(id);

          setForm({
            name: dest.name,
            description: dest.description,
            imageUrl: dest.imageUrl || "/images/default.jpg",
            countryId: dest.countryId?.toString() || "",
          });

          // 3- IMPORTANT: map tag IDs (numbers)
          const tagIds = (dest.tags || [])
            .map(tagName =>
              tagsData.find(t => t.name === tagName)?.id
            )
            .filter(Boolean);

          setSelectedTags(tagIds);          
          console.log("SELECTED:", tagIds);
        }
      } catch (err) {
        console.error("Failed to load destination form data", err);
      }
    };

    loadFormData();
  }, [id, isEdit, navigate]);


  const handleChange = (e) => {
    setErrorMessage("");
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let imageUrlToSend = form.imageUrl;
    if (selectedFile) imageUrlToSend = `./images/${selectedFile.name}`;

    const payload = {
      name: form.name,
      description: form.description,
      imageUrl: imageUrlToSend,
      countryId: Number(form.countryId),
    };

    try {
      let destinationId;
      if (isEdit) {
        await destinationAPI.update(id, payload);
        destinationId = id;

        // clear all tags first
        await destinationAPI.clearTags(destinationId);
      } else {
        const createdResponse = await destinationAPI.create(payload);
        destinationId = createdResponse.data.id;
      }

      if (selectedTags.length) {
        const uniqueTags = [...new Set(selectedTags)];
        await destinationAPI.addAllTags(destinationId, uniqueTags);
      }

      navigate("/destinations");
    } catch (err) {
      console.error(err.response || err);

      if (err.response?.status === 409) {
          setErrorMessage(err.response.data.message);
        } else {
          setErrorMessage("Something went wrong. Please try again.");
      }            
    }
  };

  const toggleTag = (tagId) => {
    setSelectedTags(prev =>
      prev.includes(tagId)
        ? prev.filter(id => id !== tagId)
        : [...prev, tagId]
    );
  };  

  return (
    <div className="max-w-xl mx-auto mt-10 bg-white shadow-lg rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-6 text-center">
        {isEdit ? "Edit Destination" : "Add New Destination"}
      </h2>

      {errorMessage && (
        <div className="mb-4 bg-red-100 border border-red-400 text-red-700 px-4 py-2 rounded">
          {errorMessage}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          name="name"
          placeholder="Destination name"
          value={form.name}
          onChange={handleChange}
          required
          className="w-full border p-2 rounded"
        />

        <textarea
          name="description"
          placeholder="Description"
          value={form.description}
          onChange={handleChange}
          className="w-full border p-2 rounded h-24"
        />

        <div>
          <label className="block mb-1 font-medium">Image</label>
          <input
            type="file"
            accept="image/*"
            onChange={(e) => {
              const file = e.target.files[0];
              if (!file) return;
              setSelectedFile(file);
              setForm(prev => ({ ...prev, imageUrl: `./images/${file.name}` }));
            }}
            className="w-full border p-2 rounded"
          />
        </div>

        {selectedFile && (
          <img
            src={URL.createObjectURL(selectedFile)}
            alt="Preview"
            className="h-48 w-full object-cover rounded mt-2"
          />
        )}

        <select
          name="countryId"
          value={form.countryId}
          onChange={handleChange}
          required
          className="w-full border p-2 rounded bg-white"
        >
          <option value="">Select a country</option>
          {countries.map(c => (
            <option key={c.id} value={c.id.toString()}>{c.name}</option>
          ))}
        </select>

        {/* Tags */}
        <div>
          <label className="block mb-1 font-medium">Tags</label>
          <div className="flex flex-wrap gap-2 mt-2">
            {tags.map(tag => {
              const selected = selectedTags.includes(tag.id);

              return (
                <button
                  type="button"
                  key={tag.id}
                  onClick={() => toggleTag(tag.id)}
                  className={`px-3 py-1 rounded-full text-sm transition ${
                    selected
                      ? "bg-blue-600 text-white"
                      : "bg-gray-200 text-gray-700 hover:bg-gray-300"
                  }`}
                >
                  #{tag.name}
                </button>
              );
            })}
          </div>
        </div>

        <div className="flex gap-2 mt-4">
          <button
            type="submit"
            className="flex-1 bg-green-600 text-white py-2 rounded hover:bg-green-700"
          >
            {isEdit ? "Update Destination" : "Create Destination"}
          </button>
          <button
            type="button"
            onClick={() => navigate("/destinations")}
            className="flex-1 bg-gray-300 text-gray-800 py-2 rounded hover:bg-gray-400"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default DestinationForm;