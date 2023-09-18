package cm.Bangoulap.repository;

import cm.Bangoulap.entities.Association;
import cm.Bangoulap.entities.Gift;
import cm.Bangoulap.user.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    Optional <Gift> findGiftByIdGift(Long idGift);
    List<Gift> findGiftByEvent(Long idEvent);
    List<Gift> findGiftByMoyenDePaiement(String moyen);
    List<Gift> findGiftByMembre(AppUser membre);
    List<Gift> findGiftByAssociation(Association association);
    List<Gift> findGiftByAmountGreaterThanEqual(int value);


}
