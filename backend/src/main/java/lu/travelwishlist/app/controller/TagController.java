package lu.travelwishlist.app.controller;

import jakarta.validation.Valid;
import lu.travelwishlist.app.dto.request.CreateTagRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateTagRequestDTO;
import lu.travelwishlist.app.entity.Tag;
import lu.travelwishlist.app.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;


    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Tag> createTag(@Valid @RequestBody CreateTagRequestDTO dto) {
        return ResponseEntity.ok(tagService.createTag(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @Valid @RequestBody UpdateTagRequestDTO dto) {
        return ResponseEntity.ok(tagService.updateTag(id, dto));
    }

}
