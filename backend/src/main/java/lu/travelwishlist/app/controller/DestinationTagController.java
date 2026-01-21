package lu.travelwishlist.app.controller;


import io.swagger.v3.oas.annotations.Operation;
import lu.travelwishlist.app.service.DestinationTag.DestinationTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/destination-tags")
public class DestinationTagController {

    private final DestinationTagService destinationTagService;

    public DestinationTagController(DestinationTagService destinationTagService) {
        this.destinationTagService = destinationTagService;
    }

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
}