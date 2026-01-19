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
  const [form, setForm] = useState({
    name: "",
    description: "",
    imageUrl: "/images/default.jpg", // default image
    countryId: "",
  });
  const [selectedFile, setSelectedFile] = useState(null);
  

  useEffect(() => {
    if (!authAPI.isAdmin()) {
      navigate("/login");
      return;
    }

    //countryAPI.getAll().then(setCountries);
    // Load countries first
    countryAPI.getAll().then(countriesData => {
    setCountries(countriesData);

  // Only after countries are loaded, set form if editing
    if (isEdit) {
      destinationAPI.getById(id).then(data => {
        setForm({
          name: data.name,
          description: data.description,
          imageUrl: data.imageUrl,
          countryId: data.countryId?.toString() || "",
        });
      });
    }
  });
  }, [id, isEdit, navigate]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  {selectedFile && (
    <img
      src={URL.createObjectURL(selectedFile)}
      alt="Preview"
      className="h-48 w-full object-cover rounded mt-2"
    />
  )}


  const handleSubmit = async (e) => {
    e.preventDefault();

  //  let imageUrlToSend = form.imageUrl;

  // If user selected a file, we can store its path (simulate upload)
  // In real projects: send FormData to backend or upload service
  //if (selectedFile) {
  //  imageUrlToSend = `${selectedFile.name}`;
 // }
  //if (selectedFile) {
   // imageUrlToSend = URL.createObjectURL(selectedFile);
  //}  

  const payload = {
    name: form.name,
    description: form.description,
    imageUrl: form.imageUrl,
    countryId: Number(form.countryId),
  };

  try {
    if (isEdit) {
      await destinationAPI.update(id, payload);
    } else {
      await destinationAPI.create(payload);
    }
    navigate("/destinations");
  } catch (err) {
    console.error(err);
    alert("Failed to save destination");
  }
  };


  return (
    <div className="max-w-xl mx-auto mt-10 bg-white shadow-lg rounded-lg p-6">
      <h2 className="text-2xl font-bold mb-6 text-center">
        {isEdit ? "Edit Destination" : "Add New Destination"}
      </h2>

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
            name="imageUrl"
            type="file"
            accept="image/*"
            onChange={(e) => {
              const file = e.target.files[0];
              if (!file) return;
              setSelectedFile(file);

              // Simulate storing path
              setForm(prev => ({
                ...prev,
                imageUrl: `./images/${file.name}`,
              }));
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
          {countries.map(country => (
            <option key={country.id} value={country.id.toString()}>
              {country.name}
            </option>
          ))}
        </select>

        <button
          type="submit"
          className="w-full bg-green-600 text-white py-2 rounded hover:bg-green-700"
        >
          {isEdit ? "Update Destination" : "Create Destination"}
        </button>
      </form>
    </div>
  );
};

export default DestinationForm;