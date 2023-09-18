package cm.Bangoulap.repository;

import cm.Bangoulap.entities.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {

    Optional<Association> findAssociationByIdAss(Long idAss);
    // Optional<Association> findAssociationByIdCAndCommunaute(Long idCom);
    Optional<Association> findAssociationByName(String name);

    List<Association> findAssociationByTown(String town);
    List<Association> findAssociationByCommunaute(Long idCommunaute);

}
