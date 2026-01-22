package lu.travelwishlist.app.exception;

/**
 * Exception thrown when attempting to register or update a user
 * with a username that already exists in the system.
 *
 * <p>
 * Maps to HTTP 409 Conflict in the API.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new UsernameAlreadyExistsException.
     *
     * @param username the username that caused the conflict
     */
    public UsernameAlreadyExistsException(String username) {
        super(String.format("Username '%s' already exists", username));
    }
}