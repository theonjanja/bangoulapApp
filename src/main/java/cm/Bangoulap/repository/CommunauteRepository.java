package cm.Bangoulap.repository;

import cm.Bangoulap.entities.Communaute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunauteRepository extends JpaRepository<Communaute, Long> {

    Optional<Communaute> findCommunauteByIdCom(Long idCom);
    Optional<Communaute> findCommunauteByTown(String town);

}
