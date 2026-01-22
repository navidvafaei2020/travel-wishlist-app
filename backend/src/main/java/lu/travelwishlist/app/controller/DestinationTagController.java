package lu.travelwishlist.app.controller;


import io.swagger.v3.oas.annotations.Operation;
import lu.travelwishlist.app.service.DestinationTag.DestinationTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tags associated with destinations.
 *
 * <p>
 * Provides endpoints for:
 * <ul>
 *     <li>Deleting all tags from a specific destination (admin-only)</li>
 *     <li>Adding multiple tags to a destination (admin-only)</li>
 * </ul>
 *
 * <p>
 * All endpoints require the user to have the ADMIN role.
 */
@RestController
@RequestMapping("/api/destination-tags")
public class DestinationTagController {

    private final DestinationTagService destinationTagService;

    public DestinationTagController(DestinationTagService destinationTagService) {
        this.destinationTagService = destinationTagService;
    }


    /**
     * Deletes all tags associated with a given destination.
     *
     * @param destinationId the ID of the destination
     * @return empty response with HTTP 204
     */
    @Operation(
            summary = "Remove all tags from a destination",
            description = "Deletes all tags associated with the given destination ID. Admin only."
    )
    @DeleteMapping("/destination/{destinationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllTagsFromDestination(
            @PathVariable Long destinationId) {

        destinationTagService.deleteAllTagsFromDestination(destinationId);
        return ResponseEntity.noContent().build();
    }



    /**
     * Assigns multiple tags to a destination.
     *
     * @param destinationId the ID of the destination
     * @param tagIds        list of tag IDs to assign
     * @return empty response with HTTP 200
     */
    @Operation(
            summary = "Add multiple tags to a destination",
            description = "Assigns a list of tags to a destination. Admin only."
    )
    @PostMapping("/destination/{destinationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addAllTagsToDestination(
            @PathVariable Long destinationId,
            @RequestBody List<Long> tagIds) {

        destinationTagService.addAllTagsToDestination(destinationId, tagIds);
        return ResponseEntity.ok().build();
    }
}