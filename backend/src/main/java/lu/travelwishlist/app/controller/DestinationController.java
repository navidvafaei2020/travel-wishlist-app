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

/**
 * REST controller for managing destinations.
 *
 * <p>
 * Provides CRUD operations for destinations and endpoints for retrieving
 * destinations by tags. Admin-only operations are secured with JWT.
 */
@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private DestinationTagService destinationTagService;



    /**
     * Retrieves all destinations with associated tags and countries.
     *
     * @return list of {@link DestinationResponseDTO}
     */
    @Operation(
            summary = "Get all destinations",
            description = "Returns a list of all destinations with their associated tags and country."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<DestinationResponseDTO>> getAll() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }



    /**
     * Retrieves a specific destination by its ID.
     *
     * @param id destination ID
     * @return {@link DestinationResponseDTO} for the given ID
     */
    @Operation(
            summary = "Get destination by ID",
            description = "Returns details of a specific destination by its ID."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.getDestinationById(id));
    }



    /**
     * Suggests destinations based on provided tags using external API integration.
     *
     * @param tags comma-separated tags
     * @return list of suggested destinations as JSON string
     */
    @Operation(
            summary = "Suggests destination by receiving tags using external API integration",
            description = "Returns list of destinations by receiving tags using external API integration."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/tags/{tags}")
    public ResponseEntity<String> getByTags(@PathVariable String tags) {
        return ResponseEntity.ok(destinationService.getDestinationByTags(tags));
    }



    /**
     * Creates a new destination. Admin-only action.
     *
     * @param dto request data for creating a destination
     * @return the created {@link DestinationResponseDTO}
     */
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



    /**
     * Updates an existing destination by ID. Admin-only action.
     *
     * @param id  destination ID
     * @param dto request data for updating a destination
     * @return the updated {@link DestinationResponseDTO}
     */
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



    /**
     * Deletes a destination by ID. Admin-only action.
     *
     * @param id destination ID
     * @return empty response with HTTP 204
     */
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

}
