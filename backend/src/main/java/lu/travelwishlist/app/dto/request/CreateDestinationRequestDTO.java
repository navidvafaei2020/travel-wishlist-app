package lu.travelwishlist.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CreateDestinationRequestDTO {

    @NotBlank(message = "Destination name is required")
    @Size(min = 2, max = 100, message = "Destination name must be between 2 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Country is required")
    private Long countryId;   // Reference to Country entity (FK)




    // Constructors, getter, setters, ...
    public CreateDestinationRequestDTO() {}

    public CreateDestinationRequestDTO(String name, String description, String imageUrl, Long countryId) {
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
