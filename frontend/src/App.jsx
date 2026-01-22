import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./pages/Login";
import Destinations from "./pages/Destinations";
import Wishlist from "./pages/Wishlist";
import DestinationForm from "./pages/admin/DestinationForm";
import TagManagement from "./pages/admin/TagManagement";
import Register from "./pages/Register";
import About from "./pages/About";
import { authAPI } from "./services/authService";


/**
 * App component sets up the main routing structure of the Travel Wishlist frontend.
 * 
 * <p>Uses <code>react-router-dom</code> for navigation and route guarding.</p>
 * 
 * Route Guards:
 * <ul>
 *   <li><code>ProtectedRoute</code> – Ensures the user is authenticated (USER or ADMIN). Redirects to login if not.</li>
 *   <li><code>AdminRoute</code> – Ensures the user is an ADMIN. Redirects to login if not.</li>
 * </ul>
 * 
 * Routes:
 * <ul>
 *   <li><code>/login</code> – Public login page.</li>
 *   <li><code>/register</code> – Public registration page.</li>
 *   <li><code>/about</code> – Public about page.</li>
 *   <li><code>/destinations</code> – Protected route accessible to USER or ADMIN.</li>
 *   <li><code>/wishlist</code> – Protected route accessible to USER only.</li>
 *   <li><code>/admin/destinations/new</code> – Admin-only route for creating destinations.</li>
 *   <li><code>/admin/destinations/edit/:id</code> – Admin-only route for editing destinations.</li>
 *   <li><code>/admin/tags</code> – Admin-only route for managing tags.</li>
 *   <li><code>*</code> – Fallback route redirects to login.</li>
 * </ul>
 * 
 * Components:
 * <ul>
 *   <li><code>Navbar</code> – Top navigation bar shown on all routes.</li>
 * </ul>
 */



/* ---------- Route Guards ---------- */

const ProtectedRoute = ({ children }) => {
  const role = authAPI.getUserRole();
  if (!role) return <Navigate to="/login" replace />;
  return children;
};

const AdminRoute = ({ children }) => {
  const role = authAPI.getUserRole();
  if (role !== "ADMIN") return <Navigate to="/login" replace />;
  return children;
};


/* ---------- App ---------- */

function App() {
  return (
    <Router>
      <Navbar />

      <Routes>
        {/* Start app from login */}
        <Route path="/" element={<Navigate to="/login" replace />} />

        {/* Public */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/about" element={<About />} />

        {/* Protected (USER / ADMIN) */}
        <Route
          path="/destinations"
          element={
            <ProtectedRoute>
              <Destinations />
            </ProtectedRoute>
          }
        />

        <Route
          path="/wishlist"
          element={
            <ProtectedRoute>
              <Wishlist />
            </ProtectedRoute>
          }
        />

        {/* ADMIN only */}
        <Route
          path="/admin/destinations/new"
          element={
            <AdminRoute>
              <DestinationForm />
            </AdminRoute>
          }
        />

        <Route
          path="/admin/destinations/edit/:id"
          element={
            <AdminRoute>
              <DestinationForm />
            </AdminRoute>
          }
        />

        <Route
          path="/admin/tags"
          element={
            <AdminRoute>
              <TagManagement />
            </AdminRoute>
          }
        />

        {/* Fallback */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

export default App;
