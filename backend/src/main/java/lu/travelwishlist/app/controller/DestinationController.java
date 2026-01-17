package lu.travelwishlist.app.controller;

import jakarta.validation.Valid;
import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;
import lu.travelwishlist.app.service.DestinationTag.DestinationTagService;
import lu.travelwishlist.app.service.destination.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
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



    @GetMapping
    public ResponseEntity<List<DestinationResponseDTO>> getAll() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(destinationService.getDestinationById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DestinationResponseDTO> create(@Valid @RequestBody CreateDestinationRequestDTO dto) {
        return ResponseEntity.ok(destinationService.createDestination(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateDestinationRequestDTO dto) {
        return ResponseEntity.ok(destinationService.updateDestination(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }

    // Tag endpoints - admin only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{destinationId}/tags/{tagId}")
    public ResponseEntity<Void> addTag(@PathVariable Long destinationId,
                                       @PathVariable Long tagId) {
        destinationTagService.addTagToDestination(destinationId, tagId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{destinationId}/tags/{tagId}")
    public ResponseEntity<Void> removeTag(@PathVariable Long destinationId,
                                          @PathVariable Long tagId) {
        destinationTagService.removeTagFromDestination(destinationId, tagId);
        return ResponseEntity.ok().build();
    }

}
