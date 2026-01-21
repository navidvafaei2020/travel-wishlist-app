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

@Service
public class DestinationTagServiceImpl implements DestinationTagService {

    private final DestinationRepository destinationRepository;
    private final TagRepository tagRepository;
    private final DestinationTagRepository destinationTagRepository;

    public DestinationTagServiceImpl(
            DestinationRepository destinationRepository,
            TagRepository tagRepository,
            DestinationTagRepository destinationTagRepository
    ) {
        this.destinationRepository = destinationRepository;
        this.tagRepository = tagRepository;
        this.destinationTagRepository = destinationTagRepository;
    }

    @Override
    public void addTagToDestination(Long destinationId, Long tagId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        // Prevent duplicates
        boolean exists = destinationTagRepository
                .findByDestinationOrderByTagNameAsc(destination)
                .stream()
                .anyMatch(dt -> dt.getTag().getId().equals(tagId));

        if (exists) {
            throw  new DuplicateResourceException("Tag " + tag.getName() , destination.getName(), tag.getName());
        }

        DestinationTag destinationTag = new DestinationTag();
        destinationTag.setDestination(destination);
        destinationTag.setTag(tag);

        destinationTagRepository.save(destinationTag);
    }


    @Override
    public void removeTagFromDestination(Long destinationId, Long tagId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        DestinationTag destinationTag = destinationTagRepository.findByDestinationOrderByTagNameAsc(destination)
                .stream()
                .filter(dt -> dt.getTag().getId().equals(tagId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tag not assigned to destination"));

        destinationTagRepository.delete(destinationTag);
    }


    @Override
    @Transactional
    public void deleteAllTagsFromDestination(Long destinationId) {

        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        destinationTagRepository.deleteByDestination(destination);
    }



}
