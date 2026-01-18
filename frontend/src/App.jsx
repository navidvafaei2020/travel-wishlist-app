import travelLogo from './assets/travel-logo.png'
import './App.css'


const App = () => {
  return (
    <div className="container mx-auto px-4 py-8">
      {/* Hero Section */}
      <div className="mb-12">
        <img
          src={travelLogo}
          alt="Travel WishList"
          className="h-12 md:h-14 mb-3"
        />

        <h2 className="text-xl md:text-2xl text-gray-800 font-semibold mb-1">
          Travel wishlist{" "}
        </h2>

      </div>
    </div>
  );
};

export default App;