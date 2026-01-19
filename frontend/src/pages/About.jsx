import { FaGlobe } from "react-icons/fa";

const About = () => {
  return (
    <div className="max-w-3xl mx-auto mt-16 text-center">
      <FaGlobe className="text-5xl text-green-600 mx-auto mb-4" />
      <h2 className="text-3xl font-bold mb-4">
        About This Project
      </h2>
      <p className="text-gray-700">
        This Travel Wishlist application allows users to browse destinations
        and save their favorite places, while admins manage available destinations.
      </p>
    </div>
  );
};

export default About;