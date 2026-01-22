package lu.travelwishlist.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating an existing tag.
 *
 * <p>
 * Contains the name of the tag with validation to ensure it is not blank
 * and does not exceed 50 characters.
 */
public class UpdateTagRequestDTO {
    /** Name of the tag. Required, max 50 characters */
    @NotBlank(message = "Tag name is required")
    @Size(max = 50, message = "Tag name must not exceed 50 characters")
    private String name;



    // Constructors, getters, setters, ...
    public UpdateTagRequestDTO() {}

    public UpdateTagRequestDTO(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
