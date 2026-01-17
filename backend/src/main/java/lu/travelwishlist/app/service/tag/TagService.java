package lu.travelwishlist.app.service.tag;

import lu.travelwishlist.app.dto.request.CreateTagRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateTagRequestDTO;
import lu.travelwishlist.app.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();

    Tag getTagById(Long id);

    Tag createTag(CreateTagRequestDTO dto);

    Tag updateTag(Long id, UpdateTagRequestDTO dto);

}
