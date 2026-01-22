package lu.travelwishlist.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lu.travelwishlist.app.dto.request.CreateTagRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateTagRequestDTO;
import lu.travelwishlist.app.entity.Tag;
import lu.travelwishlist.app.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tags.
 *
 * <p>
 * Provides CRUD operations for tags. Admin-only operations are secured with JWT.
 */
@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;



    /**
     * Retrieves all tags.
     *
     * @return list of {@link Tag}
     */
    @Operation(
            summary = "Get all tags",
            description = "Returns a list of all tags that can be assigned to destinations."
    )
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }



    /**
     * Retrieves a specific tag by its ID.
     *
     * @param id tag ID
     * @return {@link Tag} for the given ID
     */
    @Operation(
            summary = "Get tag by ID",
            description = "Returns details of a specific tag by its ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getTagById(id));
    }



    /**
     * Creates a new tag. Admin-only action.
     *
     * @param dto request data for creating a tag
     * @return the created {@link Tag}
     */
    @Operation(
            summary = "Create tag",
            description = "Creates a new tag. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Tag> createTag(@Valid @RequestBody CreateTagRequestDTO dto) {
        Tag created = tagService.createTag(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    /**
     * Updates an existing tag. Admin-only action.
     *
     * @param id  tag ID
     * @param dto request data for updating a tag
     * @return the updated {@link Tag}
     */
    @Operation(
            summary = "Update tag",
            description = "Updates an existing tag. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @Valid @RequestBody UpdateTagRequestDTO dto) {
        return ResponseEntity.ok(tagService.updateTag(id, dto));
    }


    /**
     * Deletes a tag by ID. Admin-only action.
     *
     * @param id tag ID
     * @return empty response with HTTP 204
     */
    @Operation(
            summary = "Delete tag",
            description = "Deletes a tag by ID. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
