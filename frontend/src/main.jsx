import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

/**
 * Entry point of the Travel Wishlist React application.
 * 
 * <p>Uses React 18's <code>createRoot</code> API to render the application.</p>
 * 
 * Features:
 * <ul>
 *   <li>Wraps the <code>App</code> component in <code>StrictMode</code> for highlighting potential problems in development.</li>
 *   <li>Imports global styles from <code>index.css</code>.</li>
 *   <li>Mounts the app to the DOM element with id <code>root</code>.</li>
 * </ul>
 */

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
