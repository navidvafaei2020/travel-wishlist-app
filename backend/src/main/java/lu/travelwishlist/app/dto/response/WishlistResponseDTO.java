package lu.travelwishlist.app.dto.response;

import java.util.List;

public class WishlistResponseDTO {

    private Long wishlistId;
    private Long destinationId;
    private String destinationName;
    private String description;
    private String imageUrl;
    private Long countryId;
    private String countryName;
    private List<String> tags;




    // Constructors, getters, setters, ...
    public WishlistResponseDTO() {}

    public WishlistResponseDTO(Long wishlistId, Long destinationId, String destinationName,
                               String description, String imageUrl, Long countryId, String countryName,
                               List<String> tags) {
        this.wishlistId = wishlistId;
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.countryId = countryId;
        this.countryName = countryName;
        this.tags = tags;
    }

    public Long getWishlistId() { return wishlistId; }
    public void setWishlistId(Long wishlistId) { this.wishlistId = wishlistId; }

    public Long getDestinationId() { return destinationId; }
    public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }

    public String getDestinationName() { return destinationName; }
    public void setDestinationName(String destinationName) { this.destinationName = destinationName; }

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
