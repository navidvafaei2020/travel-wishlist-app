package lu.travelwishlist.app.exception;

/**
 * Exception thrown when a user attempts to access a resource or perform an action
 * without proper authentication.
 *
 * <p>
 * Maps to HTTP 401 Unauthorized in the API.
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified message.
     *
     * @param message the detail message explaining the unauthorized access
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}