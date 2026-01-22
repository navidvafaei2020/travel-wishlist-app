package lu.travelwishlist.app.service.tag;

import jakarta.transaction.Transactional;
import lu.travelwishlist.app.dto.request.CreateTagRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateTagRequestDTO;
import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.Tag;
import lu.travelwishlist.app.exception.DuplicateResourceException;
import lu.travelwishlist.app.mapper.TagMapper;
import lu.travelwishlist.app.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing Tag entities.
 * Provides methods to create, read, update, and delete tags.
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;


    /**
     * Constructor to initialize the Tag repository.
     *
     * @param tagRepository Repository for Tag entities.
     */
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    /**
     * Retrieves all tags.
     *
     * @return List of all Tag entities.
     */
    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }


    /**
     * Retrieves a tag by its ID.
     *
     * @param id ID of the tag.
     * @return Tag entity.
     * @throws RuntimeException if the tag is not found.
     */
    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }


    /**
     * Creates a new tag.
     * Throws an exception if a tag with the same name already exists.
     *
     * @param dto DTO containing tag creation data.
     * @return Created Tag entity.
     * @throws DuplicateResourceException if a tag with the same name exists.
     */
    @Override
    public Tag createTag(CreateTagRequestDTO dto) {
        if (tagRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new DuplicateResourceException("Tag", "name", dto.getName());
        }

        Tag tag = TagMapper.toEntity(dto);
        return tagRepository.save(tag);
    }


    /**
     * Updates an existing tag.
     * Ensures the new name does not duplicate another tag's name.
     *
     * @param id ID of the tag to update.
     * @param dto DTO containing updated tag data.
     * @return Updated Tag entity.
     * @throws DuplicateResourceException if another tag with the same name exists.
     */
    @Override
    public Tag updateTag(Long id, UpdateTagRequestDTO dto) {
        Tag tag = getTagById(id);
        if (tagRepository.existsByNameIgnoreCaseAndIdNot(dto.getName(), id)) {
            throw new DuplicateResourceException("Tag", "name", dto.getName());
        }
        tag.setName(dto.getName());
        return tagRepository.save(tag);
    }



    /**
     * Deletes a tag by its ID.
     *
     * @param id ID of the tag to delete.
     * @throws RuntimeException if the tag is not found.
     */
    @Override
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tagRepository.delete(tag);
    }
}
