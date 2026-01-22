package lu.travelwishlist.app.service.DestinationTag;

import java.util.List;

public interface DestinationTagService {

    //void addTagToDestination(Long destinationId, Long tagId);

    void addAllTagsToDestination(Long destinationId, List<Long> tagIds);

    void removeTagFromDestination(Long destinationId, Long tagId);

    void deleteAllTagsFromDestination(Long destinationId);

}
