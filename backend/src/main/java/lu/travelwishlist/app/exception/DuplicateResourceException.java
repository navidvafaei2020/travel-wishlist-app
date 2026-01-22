package lu.travelwishlist.app.exception;

/**
 * Exception thrown when attempting to create a resource that already exists
 * based on a unique field.
 *
 * <p>
 * Maps to HTTP 409 Conflict in the API.
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Constructs a new DuplicateResourceException with a descriptive message.
     *
     * @param resource the type of resource (e.g., "Destination", "Tag")
     * @param field the field that caused the conflict (e.g., "name", "email")
     * @param value the value of the field that already exists
     */
    public DuplicateResourceException(String resource, String field, String value) {
        super(String.format("%s with %s %s already exist!",resource, field, value));
    }
}
