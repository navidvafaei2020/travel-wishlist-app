package lu.travelwishlist.app.service.destination;

import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;

import java.util.List;

/**
 * Service interface for managing destinations.
 *
 * <p>Supports CRUD operations, AI-based suggestions by tags, and retrieval of destinations with associated tags.</p>
 */
public interface DestinationService {
    /** Retrieves all destinations with their associated tags and country info. */
    List<DestinationResponseDTO> getAllDestinations();

    /** Retrieves a destination by its ID. */
    DestinationResponseDTO getDestinationById(Long id);

    /** Suggests destinations based on provided tags via external AI API. */
    String getDestinationByTags(String tags);

    /** Creates a new destination. Throws DuplicateResourceException if a destination with the same name exists in the country. */
    DestinationResponseDTO createDestination(CreateDestinationRequestDTO dto);

    /** Updates an existing destination by ID. Throws DuplicateResourceException if name conflicts with another destination in the same country. */
    DestinationResponseDTO updateDestination(Long id, UpdateDestinationRequestDTO dto);

    /** Deletes a destination, including associated tags and wishlist entries. */
    void deleteDestination(Long id);
}