package cm.Bangoulap.service;

import cm.Bangoulap.dto.EventDto;
import cm.Bangoulap.entities.Event;
import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.exception.ErrorCodes;
import cm.Bangoulap.exception.InvalidEntityException;
import cm.Bangoulap.repository.EventRepository;
import cm.Bangoulap.user.appuser.AppUser;
import cm.Bangoulap.user.appuser.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AppUserRepository appUserRepository;

    public ResponseEntity saveEvent(Event event) {
        log.info("Création d'un événement par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity;
        if(event == null) {
            // TODO: Retourner une ResponseEntity.
            throw new InvalidEntityException("La valeur de la variable \"event\" est nulle.", ErrorCodes.INVALID_ENTITY);
        }
        event.setName(event.getName().toUpperCase());
        Optional<Event> evt = eventRepository.findEventByName(event.getName());
        if(evt.isPresent() && event.getName().equals(evt.get().getName())) {
            responseEntity = new ResponseEntity(1000, "Un Evénement de même nom existe déjà. ", "FAILED", null);
            log.error("Error: Un Evénement de même nom existe déjà.");
        }else {
            AppUser userWhoCreate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            event.setCreateBy(userWhoCreate);
            event.setUpdateBy(userWhoCreate);

            Event createdEvent = eventRepository.save(event);

            if(createdEvent != null) {
                log.info("Evénement créé avec succès !");
                responseEntity = new ResponseEntity(200, "Evénement créé avec sussès !", "SUCCESS", EventDto.fromEntity(createdEvent));
            }else {
                log.error("Une erreur s'est produite lors de la création de l'événement.");
                responseEntity = new ResponseEntity(501, "Une erreur s'est produite lors de la création de l'événement.", "FAILED", null);
            }
        }
        return responseEntity;
    }

    public ResponseEntity updateEvent(Event event) {
        log.info("Modification de l'événement par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity;
        if (event == null) {
            throw new InvalidEntityException("La valeur de la variable \"event\" est nulle.", ErrorCodes.INVALID_ENTITY);
        }
        event.setName(event.getName().toUpperCase());
        Optional<Event> evt = eventRepository.findEventByName(event.getName());
        if ((!evt.isPresent()) || (evt.get().getIdEvent() == (event.getIdEvent()))) {
            Event eventToUpdate = eventRepository.findEventByIdEvent(event.getIdEvent()).get();
            if (eventToUpdate != null) {
                event.setCreateBy(eventToUpdate.getCreateBy());
                AppUser userWhoUpdate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
                event.setUpdateBy(userWhoUpdate);
                event.setCreationDate(eventToUpdate.getCreationDate());
                eventToUpdate = eventRepository.save(event);
                log.info("Evénement mis à jour avec succès !");
                responseEntity = new ResponseEntity(200, "Evénement mis à jour avec succès !", "SUCCESS", EventDto.fromEntity(eventToUpdate));
            } else {
                responseEntity = new ResponseEntity(1002, "L'événement que vous voulez modifier n'existe pas.", "FAILED", null);
                log.error("Error: L'événement que vous voulez modifier n'existe pas.");
            }
        }else {
            responseEntity = new ResponseEntity(1000, "Un événement de même nom existe déjà.", "FAILED", null);
            log.error("Error: Un événement de même nom existe déjà.");

        }
        return responseEntity;
    }

    public ResponseEntity getEventById(Long idEvent) {
        log.info("Recherche de l'événement d'ID " + idEvent + " par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        Event event = eventRepository.findEventByIdEvent(idEvent).get(); // TODO: resustat de la BD peut être nul.
        ResponseEntity responseEntity = new ResponseEntity();
        if(event != null) {
            responseEntity.setCode(200);
            responseEntity.setStatus("SUCCESS");
            responseEntity.setMessage("Recherche fructueuse !");
            responseEntity.setValue(EventDto.fromEntity(event));
            log.info("SUCCESS: Evénement trouvé !");
        }else {
            responseEntity.setCode(1002);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("Aucun événement n'existe avec l'identifiant fourni");
            responseEntity.setValue(null);
            log.info("ERROR: Aucun événement n'existe avec l'identifiant fourni.");
        }

        return responseEntity;
    }

    public ResponseEntity getAllEvent() {
        log.info("Recherche de la liste des événements par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());

        List<Event> events = eventRepository.findAll();
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(200);
        responseEntity.setStatus("SUCCESS");
        if(events.isEmpty()) {
            responseEntity.setMessage("La liste des évements est vide.");
            log.info("SUCCESS: Aucun événement n'est enregistré.");
        }else {
            responseEntity.setMessage("Liste des événements.");
            log.info("SUCCESS: Evénement(s) trouvé(s) !");
        }
        responseEntity.setValue(
                events.stream()
                        .map(event -> EventDto.fromEntity(event))
                        .collect(Collectors.toList())
                );
        return responseEntity;
    }

    public ResponseEntity getEventsByYear(int year) {
        log.info("Recherche de la liste des événements de l'année " + year + " par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());

        List<Event> events = eventRepository.findEventByEventYear(year);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(200);
        responseEntity.setStatus("SUCCESS");
        if(events.isEmpty()) {
            responseEntity.setMessage("La liste des évements de l'année " + year + " est vide.");
            log.info("SUCCESS: Aucun événement n'est enregistré pour l'année "+ year + ".");
        }else {
            responseEntity.setMessage("Liste des événements de l'année " + year + ".");
            log.info("SUCCESS: Evénement(s) trouvé(s) !");
        }
        responseEntity.setValue(
                events.stream()
                        .map(event -> EventDto.fromEntity(event))
                        .collect(Collectors.toList())
        );
        return responseEntity;
    }


}
