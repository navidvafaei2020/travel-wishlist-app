package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.request.CreateTagRequestDTO;
import lu.travelwishlist.app.entity.Tag;

/**
 * Mapper class for converting between Tag entities and DTOs.
 *
 * <p>
 * Provides methods for entity → DTO conversion and DTO → entity creation.
 */
public class TagMapper {

    /**
     * Converts a Tag entity to a CreateTagRequestDTO.
     *
     * @param tag the Tag entity
     * @return a CreateTagRequestDTO containing the tag's name
     */
    public static CreateTagRequestDTO toDto(Tag tag) {
        return new CreateTagRequestDTO(tag.getName());
    }


    /**
     * Converts a CreateTagRequestDTO to a new Tag entity.
     *
     * @param dto the tag creation DTO
     * @return a new Tag entity
     */
    public static Tag toEntity(CreateTagRequestDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        return tag;
    }
}
