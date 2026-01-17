package lu.travelwishlist.app.dto.response;

import java.util.List;

public class DestinationResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long countryId;
    private String countryName;
    private List<String> tags;




    // Constructors, getters, setters, ...
    public DestinationResponseDTO() {}

    public DestinationResponseDTO(Long id, String name, String description, String imageUrl,
                                  Long countryId, String countryName, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.countryId = countryId;
        this.countryName = countryName;
        this.tags = tags;
    }

    // Getters and Setters (one-line style)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Long getCountryId() { return countryId; }
    public void setCountryId(Long countryId) { this.countryId = countryId; }

    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

}
