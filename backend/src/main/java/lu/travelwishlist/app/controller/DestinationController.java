package lu.travelwishlist.app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;
import lu.travelwishlist.app.service.DestinationTag.DestinationTagService;
import lu.travelwishlist.app.service.destination.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private DestinationTagService destinationTagService;



    @Operation(
            summary = "Get all destinations",
            description = "Returns a list of all destinations with their associated tags and country."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<DestinationResponseDTO>> getAll() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }


    @Operation(
            summary = "Get destination by ID",
            description = "Returns details of a specific destination by its ID."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.getDestinationById(id));
    }


    @Operation(
            summary = "Suggests destination by receiving tags using external API integration",
            description = "Returns list of destinations by receiving tags using external API integration."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/tags/{tags}")
    public ResponseEntity<String> getByTags(@PathVariable String tags) {
        return ResponseEntity.ok(destinationService.getDestinationByTags(tags));
    }


    @Operation(
            summary = "Create destination",
            description = "Creates a new destination. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DestinationResponseDTO> create(@Valid @RequestBody CreateDestinationRequestDTO dto) {
        DestinationResponseDTO created = destinationService.createDestination(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @Operation(
            summary = "Update destination",
            description = "Updates an existing destination. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateDestinationRequestDTO dto) {
        return ResponseEntity.ok(destinationService.updateDestination(id, dto));
    }


    @Operation(
            summary = "Delete destination",
            description = "Deletes a destination by ID. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }


    // Tag endpoints - admin only
    @Operation(
            summary = "Add tag",
            description = "Adds a new tag. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{destinationId}/tags/{tagId}")
    public ResponseEntity<Void> addTag(@PathVariable Long destinationId,
                                       @PathVariable Long tagId) {
        destinationTagService.addTagToDestination(destinationId, tagId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(
            summary = "Removes tag",
            description = "Removes an existing tag. Only ADMIN users can perform this action.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{destinationId}/tags/{tagId}")
    public ResponseEntity<Void> removeTag(@PathVariable Long destinationId,
                                          @PathVariable Long tagId) {
        destinationTagService.removeTagFromDestination(destinationId, tagId);
        return ResponseEntity.ok().build();
    }


}
