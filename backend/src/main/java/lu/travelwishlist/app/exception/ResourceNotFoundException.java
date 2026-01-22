package lu.travelwishlist.app.exception;

/**
 * Exception thrown when a requested resource is not found in the system.
 *
 * <p>
 * Maps to HTTP 404 Not Found in the API.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with a message indicating
     * the resource type and its ID.
     *
     * @param resource the name of the resource (e.g., "User", "Destination")
     * @param id the ID of the resource that was not found
     */
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s not found with id: %d", resource, id));
    }
}