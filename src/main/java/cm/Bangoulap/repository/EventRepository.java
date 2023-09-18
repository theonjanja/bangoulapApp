package cm.Bangoulap.repository;

import cm.Bangoulap.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findEventByIdEvent(Long idEvent);
    Optional<Event> findEventByName(String name);
    List<Event> findEventByEventYear(int year);
}
