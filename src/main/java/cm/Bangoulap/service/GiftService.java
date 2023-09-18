package cm.Bangoulap.service;

import cm.Bangoulap.dto.EventDto;
import cm.Bangoulap.dto.GiftDto;
import cm.Bangoulap.entities.*;
import cm.Bangoulap.entities.validator.GiftRequestValidator;
import cm.Bangoulap.exception.ErrorCodes;
import cm.Bangoulap.exception.InvalidEntityException;
import cm.Bangoulap.repository.AssociationRepository;
import cm.Bangoulap.repository.EventRepository;
import cm.Bangoulap.repository.GiftRepository;
import cm.Bangoulap.user.appuser.AppUser;
import cm.Bangoulap.user.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class GiftService {

    private final GiftRepository giftRepository;
    private final AppUserRepository appUserRepository;
    private final EventRepository eventRepository;
    private final AssociationRepository associationRepository;

    public ResponseEntity saveEvent(GiftRequest giftRequest) {
        log.info("Paiement d'un don par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity;
        List<String> errors = GiftRequestValidator.validate(giftRequest);
        if(!errors.isEmpty()) {
            responseEntity = new ResponseEntity(1001, "L'objet fourni est invalide. ", "FAILED", errors);
            log.error(errors.toString());
            return responseEntity;
        }

        Gift gift = new Gift();
        Optional<AppUser> membre = appUserRepository.findById(giftRequest.getIdMembre());
        if(!membre.isPresent()) {
            responseEntity = new ResponseEntity(1001, "Le membre pour qui vous voulez payer le don n'existe pas.", "FAILED", null);
            log.error("Le membre pour qui vous voulez payer le don n'existe pas.");
            return responseEntity;
        }
        log.info("Don réalisé au compte de: " + membre.get().getFirstName() + " " + membre.get().getLastName());
        gift.setMembre(membre.get());

        Optional<AppUser> payeur = appUserRepository.findById(giftRequest.getIdPayeur());
        if(!payeur.isPresent()) {
            responseEntity = new ResponseEntity(1001, "Le payeur que vous avez renseigné n'existe pas.", "FAILED", null);
            log.error("Le payeur que vous avez renseigné n'existe pas.");
            return responseEntity;
        }
        gift.setPayeur(payeur.get());

        Optional<Event> event = eventRepository.findEventByIdEvent(giftRequest.getIdEvent());
        if(!event.isPresent()) {
            responseEntity = new ResponseEntity(1001, "L'événement pour lequel vous voulez faire le don n'existe pas.", "FAILED", null);
            log.error("L'événement pour lequel vous voulez faire le don n'existe pas.");
            return responseEntity;
        }
        log.info("Don réalisé au compte de l'événement: " + event.get().getName());
        gift.setEvent(event.get());

        if(giftRequest.getIdAssociation() != null) {
            Optional<Association> association = associationRepository.findAssociationByIdAss(giftRequest.getIdAssociation());
            if(!association.isPresent()) {
                responseEntity = new ResponseEntity(1001, "L'association au compte de laquelle vous voulez faire le don n'existe pas.", "FAILED", null);
                log.error("L'association au compte de laquelle vous voulez faire le don n'existe pas.");
                return responseEntity;
            }
            log.info("Don réalisé au compte de l'association: " + association.get().getName());
            gift.setAssociation(association.get());
        }

        log.info("Montant du don: " + giftRequest.getAmount());
        gift.setAmount(giftRequest.getAmount());

        log.info("Moyen de paiement: " + giftRequest.getMoyenDePaiement());
        gift.setMoyenDePaiement(giftRequest.getMoyenDePaiement());

        gift = giftRepository.save(gift);

        log.info("Don réalisé avec succès !");
        responseEntity = new ResponseEntity(200, "Le don a été réalisé avec sussès !", "SUCCESS", GiftDto.fromEntity(gift));

        return responseEntity;
    }

    public ResponseEntity updateEvent(Event event) {
        // TODO
        return new ResponseEntity(); // TODO: Changer.
    }

    public ResponseEntity getGiftById(Long idGift) {
        log.info("Recherche du don d'ID " + idGift + " par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        Optional <Gift> gift = giftRepository.findGiftByIdGift(idGift);
        ResponseEntity responseEntity = new ResponseEntity();
        if(gift.isPresent()) {
            responseEntity.setCode(200);
            responseEntity.setStatus("SUCCESS");
            responseEntity.setMessage("Recherche fructueuse !");
            responseEntity.setValue(GiftDto.fromEntity(gift.get()));
            log.info("SUCCESS: Don trouvé !");
        }else {
            responseEntity.setCode(1002);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("Aucun don n'existe avec l'identifiant fourni");
            responseEntity.setValue(null);
            log.info("ERROR: Aucun don n'existe avec l'identifiant fourni.");
        }

        return responseEntity;
    }

    // TODO: Others services.
}
