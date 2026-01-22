package lu.travelwishlist.app.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Data Transfer Object for API error responses.
 *
 * <p>
 * Encapsulates error details including HTTP status, message, timestamp,
 * and optional field-specific errors.
 */
public class ErrorResponseDTO {

    /** HTTP status code of the error (e.g., 400, 404, 500) */
    private int status;
    /** General error message */
    private String message;
    /** Timestamp when the error occurred */
    private LocalDateTime timestamp;

    /** Optional field-specific validation errors (field → error message) */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;  // field → error message



    /**
     * Constructs an error response with status and message.
     *
     * @param status  HTTP status code
     * @param message error message
     */
    public ErrorResponseDTO(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an error response with status, message, and field errors.
     *
     * @param status  HTTP status code
     * @param message error message
     * @param errors  field-specific errors
     */
    public ErrorResponseDTO(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }


    // Getters and Setters
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String,String> getErrors() {
        return this.errors;
    }
    public void setErrors(Map<String,String> errors) {
        this.errors = errors;
    }
}
