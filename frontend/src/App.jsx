import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./pages/Login";
import Destinations from "./pages/Destinations";
import Wishlist from "./pages/Wishlist";
import DestinationForm from "./pages/admin/DestinationForm";
import Register from "./pages/Register";
import About from "./pages/About";
import Home from "./pages/Home";


function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/destinations" element={<Destinations />} />
        <Route path="/wishlist" element={<Wishlist />} />
        <Route path="/admin/destinations/new" element={<DestinationForm />} />
        <Route path="/admin/destinations/edit/:id" element={<DestinationForm />} />        
        <Route path="*" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
      </Routes>
    </Router>
  );
}

export default App;