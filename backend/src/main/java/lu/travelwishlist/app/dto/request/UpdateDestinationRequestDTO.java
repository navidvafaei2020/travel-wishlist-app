package lu.travelwishlist.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating an existing destination.
 *
 * <p>
 * Contains fields for name, description, image URL, and associated country.
 * Validation ensures required fields and length constraints are respected.
 */
public class UpdateDestinationRequestDTO {

    /** Name of the destination. Required, 2-100 characters */
    @NotBlank(message = "Destination name is required")
    @Size(min = 2, max = 100, message = "Destination name must be between 2 and 100 characters")
    private String name;

    /** Description of the destination. Optional, max 1000 characters */
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    /** Image URL of the destination. Required */
    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    /** ID of the country associated with the destination. Required */
    @NotNull(message = "Country is required")
    private Long countryId;



    // Constructors, getters, setters, ...
    public UpdateDestinationRequestDTO() {}

    public UpdateDestinationRequestDTO(String name, String description, String imageUrl, Long countryId) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.countryId = countryId;
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getCountryId() { return countryId; }
    public void setCountryId(Long countryId) { this.countryId = countryId; }

}
