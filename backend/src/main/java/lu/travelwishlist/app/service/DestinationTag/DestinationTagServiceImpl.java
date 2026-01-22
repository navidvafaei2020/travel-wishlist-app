package lu.travelwishlist.app.service.DestinationTag;

import jakarta.transaction.Transactional;
import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.DestinationTag;
import lu.travelwishlist.app.entity.Tag;
import lu.travelwishlist.app.exception.DuplicateResourceException;
import lu.travelwishlist.app.repository.DestinationRepository;
import lu.travelwishlist.app.repository.DestinationTagRepository;
import lu.travelwishlist.app.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service implementation for managing tags associated with destinations.
 * Provides methods to add, remove, and delete tags for a destination.
 */
@Service
public class DestinationTagServiceImpl implements DestinationTagService {

    private final DestinationRepository destinationRepository;
    private final TagRepository tagRepository;
    private final DestinationTagRepository destinationTagRepository;

    /**
     * Constructor to initialize repositories.
     *
     * @param destinationRepository Repository for Destination entities.
     * @param tagRepository Repository for Tag entities.
     * @param destinationTagRepository Repository for DestinationTag entities.
     */
    public DestinationTagServiceImpl(
            DestinationRepository destinationRepository,
            TagRepository tagRepository,
            DestinationTagRepository destinationTagRepository
    ) {
        this.destinationRepository = destinationRepository;
        this.tagRepository = tagRepository;
        this.destinationTagRepository = destinationTagRepository;
    }


    /**
     * Adds multiple tags to a destination.
     * Skips tags that are already assigned.
     *
     * @param destinationId ID of the destination.
     * @param tagIds List of tag IDs to assign.
     * @throws RuntimeException if destination or any tag is not found.
     */
    @Override
    @Transactional
    public void addAllTagsToDestination(Long destinationId, List<Long> tagIds) {

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        for (Long tagId : tagIds) {

            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("Tag not found"));

            if (!destinationTagRepository.existsByDestinationAndTag(destination, tag)) {
                destinationTagRepository.save(new DestinationTag(destination, tag));
            }
        }
    }


    /**
     * Removes a specific tag from a destination.
     *
     * @param destinationId ID of the destination.
     * @param tagId ID of the tag to remove.
     * @throws RuntimeException if destination, tag, or association is not found.
     */
    @Override
    public void removeTagFromDestination(Long destinationId, Long tagId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        DestinationTag destinationTag = destinationTagRepository.findByDestination(destination)
                .stream()
                .filter(dt -> dt.getTag().getId().equals(tagId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tag not assigned to destination"));

        destinationTagRepository.delete(destinationTag);
    }


    /**
     * Deletes all tags associated with a destination.
     *
     * @param destinationId ID of the destination.
     * @throws RuntimeException if destination is not found.
     */
    @Override
    @Transactional
    public void deleteAllTagsFromDestination(Long destinationId) {

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        destinationTagRepository.deleteByDestination(destination);
    }
}
