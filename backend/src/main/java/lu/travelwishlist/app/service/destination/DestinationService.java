package lu.travelwishlist.app.service.destination;

import lu.travelwishlist.app.dto.request.CreateDestinationRequestDTO;
import lu.travelwishlist.app.dto.request.UpdateDestinationRequestDTO;
import lu.travelwishlist.app.dto.response.DestinationResponseDTO;

import java.util.List;

public interface DestinationService {
    List<DestinationResponseDTO> getAllDestinations();

    DestinationResponseDTO getDestinationById(Long id);

    String getDestinationByTags(String tags);

    DestinationResponseDTO createDestination(CreateDestinationRequestDTO dto);

    DestinationResponseDTO updateDestination(Long id, UpdateDestinationRequestDTO dto);

    void deleteDestination(Long id);


}