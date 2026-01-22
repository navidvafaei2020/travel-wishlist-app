package lu.travelwishlist.app.service.DestinationTag;

import java.util.List;

/**
 * Service interface for managing tags associated with destinations.
 * Defines operations to add, remove, and delete tags from destinations.
 */
public interface DestinationTagService {


    /**
     * Adds multiple tags to a destination.
     * Implementations should skip tags that are already assigned.
     *
     * @param destinationId ID of the destination.
     * @param tagIds List of tag IDs to assign to the destination.
     */
    void addAllTagsToDestination(Long destinationId, List<Long> tagIds);

    /**
     * Removes a specific tag from a destination.
     *
     * @param destinationId ID of the destination.
     * @param tagId ID of the tag to remove.
     */
    void removeTagFromDestination(Long destinationId, Long tagId);

    /**
     * Deletes all tags associated with a destination.
     *
     * @param destinationId ID of the destination.
     */
    void deleteAllTagsFromDestination(Long destinationId);
}
