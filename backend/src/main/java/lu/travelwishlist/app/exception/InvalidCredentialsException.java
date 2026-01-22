package lu.travelwishlist.app.exception;

/**
 * Exception thrown when user authentication fails due to invalid
 * username or password.
 *
 * <p>
 * Maps to HTTP 401 Unauthorized in the API.
 */
public class InvalidCredentialsException extends RuntimeException {

    /** Constructs a new InvalidCredentialsException with a default message. */
    public InvalidCredentialsException() { super("Invalid username or password");
    }
}