package lu.travelwishlist.app.mapper;

import lu.travelwishlist.app.dto.request.CreateTagRequestDTO;
import lu.travelwishlist.app.entity.Tag;

public class TagMapper {

    // Entity → DTO (if needed, e.g., for list responses)
    public static CreateTagRequestDTO toDto(Tag tag) {
        return new CreateTagRequestDTO(tag.getName());
    }

    // DTO → Entity
    public static Tag toEntity(CreateTagRequestDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        return tag;
    }

}
