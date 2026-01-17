package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.DestinationTag;
import lu.travelwishlist.app.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationTagRepository extends JpaRepository<DestinationTag, Long> {

    List<DestinationTag> findByDestinationOrderByTagNameAsc(Destination destination);
    List<DestinationTag> findByTagOrderByDestinationNameAsc(Tag tag);

}
