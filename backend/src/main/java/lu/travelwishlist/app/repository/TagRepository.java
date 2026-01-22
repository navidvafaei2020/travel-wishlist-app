package lu.travelwishlist.app.repository;

import lu.travelwishlist.app.entity.Destination;
import lu.travelwishlist.app.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByNameIgnoreCase(String name);

    // To filter multiple tags at once
    List<Tag> findByNameInIgnoreCase(List<String> names);

    List<Tag> findAllByOrderByNameAsc();

    boolean existsByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

}
