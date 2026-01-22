package lu.travelwishlist.app.service.tag;

import lu.travelwishlist.app.dto.request.CreateTagRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateTagRequestDTO;
import lu.travelwishlist.app.entity.Tag;

import java.util.List;

/**
 * Service interface for managing Tag entities.
 * Defines methods for CRUD operations on tags.
 */
public interface TagService {
    /**
     * Retrieves all tags.
     *
     * @return List of all Tag entities.
     */
    List<Tag> getAllTags();


    /**
     * Retrieves a tag by its ID.
     *
     * @param id ID of the tag.
     * @return Tag entity with the specified ID.
     * @throws RuntimeException if the tag is not found.
     */
    Tag getTagById(Long id);


    /**
     * Creates a new tag.
     *
     * @param dto Data transfer object containing the tag details.
     * @return The created Tag entity.
     * @throws DuplicateResourceException if a tag with the same name already exists.
     */
    Tag createTag(CreateTagRequestDTO dto);


    /**
     * Updates an existing tag.
     *
     * @param id ID of the tag to update.
     * @param dto Data transfer object containing the updated tag details.
     * @return The updated Tag entity.
     * @throws DuplicateResourceException if another tag with the same name exists.
     */
    Tag updateTag(Long id, UpdateTagRequestDTO dto);


    /**
     * Deletes a tag by its ID.
     *
     * @param id ID of the tag to delete.
     * @throws RuntimeException if the tag is not found.
     */
    void deleteTag(Long id);
}
