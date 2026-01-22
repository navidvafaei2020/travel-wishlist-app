import { FaGlobe } from "react-icons/fa";

/**
 * About component for the Travel Wishlist application.
 * 
 * <p>Displays information about the project, including its purpose and features.</p>
 * 
 * Features:
 * - Shows a globe icon using react-icons.
 * - Provides a brief description of the application.
 * - Styled with TailwindCSS for layout and typography.
 * 
 * @component
 */

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