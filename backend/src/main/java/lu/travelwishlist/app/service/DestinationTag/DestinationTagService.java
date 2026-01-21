package lu.travelwishlist.app.service.DestinationTag;

public interface DestinationTagService {

    void addTagToDestination(Long destinationId, Long tagId);

    void removeTagFromDestination(Long destinationId, Long tagId);

    void deleteAllTagsFromDestination(Long destinationId);

}
