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

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }


    @Override
    public Tag createTag(CreateTagRequestDTO dto) {
        if (tagRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new DuplicateResourceException("Tag", "name", dto.getName());
        }

        Tag tag = TagMapper.toEntity(dto);
        return tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long id, UpdateTagRequestDTO dto) {
        Tag tag = getTagById(id);
        if (tagRepository.existsByNameIgnoreCaseAndIdNot(dto.getName(), id)) {
            throw new DuplicateResourceException("Tag", "name", dto.getName());
        }
        tag.setName(dto.getName());
        return tagRepository.save(tag);
    }


    @Override
    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tagRepository.delete(tag);
    }


}
