package lu.travelwishlist.app.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class to access the current authenticated user's information.
 *
 * <p>
 * Provides methods to retrieve user ID, role, and authentication status
 * from the Spring Security context.
 */
public class UserPrincipal {
    /**
     * Gets the ID of the currently authenticated user.
     *
     * @return the user ID, or null if not authenticated
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return (Long) authentication.getPrincipal();
    }


    /**
     * Gets the role of the currently authenticated user.
     *
     * @return the role as a String (without "ROLE_" prefix), or null if not authenticated
     */
    public static String getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getAuthorities().stream()
                .findFirst()
                .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                .orElse(null);
    }

    /**
     * Checks if a user is currently authenticated.
     *
     * @return true if authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}