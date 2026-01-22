package lu.travelwishlist.app.entity;

/**
 * Enum representing roles assigned to users in the application.
 *
 * <p>
 * - ADMIN: Can create and manage destinations.
 * - USER: Can browse destinations and manage their personal wishlist.
 */
public enum UserRole {
    ADMIN,  // Create and manage destinations
    USER    // Browse destinations and add to wishlist
}