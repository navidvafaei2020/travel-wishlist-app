package lu.travelwishlist.app.exception;

/**
 * Exception thrown when attempting to register or update a user
 * with an email that already exists in the system.
 *
 * <p>
 * Extends RuntimeException and provides a descriptive error message.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new EmailAlreadyExistsException.
     *
     * @param email the email that caused the conflict
     */
    public EmailAlreadyExistsException(String email) {
        super(String.format("Email '%s' already exists", email));
    }
}