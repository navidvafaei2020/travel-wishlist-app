package lu.travelwishlist.app.dto.response;

import java.util.List;

/**
 * Data Transfer Object for returning destination details in responses.
 *
 * <p>
 * Includes destination ID, name, description, image URL, associated country,
 * and a list of tag names.
 */
public class DestinationResponseDTO {

    /** Destination ID */
    private Long id;

    /** Name of the destination */
    private String name;

    /** Description of the destination */
    private String description;

    /** Image URL of the destination */
    private String imageUrl;

    /** ID of the associated country */
    private Long countryId;

    /** Name of the associated country */
    private String countryName;

    /** List of tag names associated with the destination */
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
