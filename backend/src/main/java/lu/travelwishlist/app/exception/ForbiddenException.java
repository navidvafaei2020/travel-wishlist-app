package lu.travelwishlist.app.exception;

/**
 * Exception thrown when a user attempts to access a resource
 * or perform an action they are not authorized to access.
 *
 * <p>
 * Maps to HTTP 403 Forbidden in the API.
 */
public class ForbiddenException extends RuntimeException {
    /**
     * Constructs a new ForbiddenException with the specified message.
     *
     * @param message the detail message explaining the forbidden action
     */
    public ForbiddenException(String message) {
        super(message);
    }
}