package cm.Bangoulap.service;

import cm.Bangoulap.dto.AssociationDto;
import cm.Bangoulap.dto.CommunauteDto;
import cm.Bangoulap.entities.Communaute;
import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.exception.EntityAlreadyExistException;
import cm.Bangoulap.exception.ErrorCodes;
import cm.Bangoulap.exception.InvalidEntityException;
import cm.Bangoulap.repository.CommunauteRepository;
import cm.Bangoulap.user.appuser.AppUser;
import cm.Bangoulap.user.appuser.AppUserRepository;
import cm.Bangoulap.user.appuser.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommunauteService {

    private final CommunauteRepository communauteRepository;
    private final AppUserRepository appUserRepository;

    public ResponseEntity saveCommunaute(Communaute communaute) {
        log.info("Création de la communauté par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity;
        if(communaute == null) {
            throw new InvalidEntityException("La valeur de la variable \"communaute\" est nulle.", ErrorCodes.INVALID_ENTITY);
        }
        if(communauteRepository.findCommunauteByTown(communaute.getTown()).isPresent()) {
            // new EntityAlreadyExistException("Une communauté existe déjà dans la même ville de " + communaute.getTown(), ErrorCodes.COMMUNAUTE_ALREADY_EXIST);
            responseEntity = new ResponseEntity(1000, "Une communauté existe déjà dans la même ville de " + communaute.getTown(), "FAILED", null);
            log.error("Error: Une communauté existe déjà dans la même ville de" + communaute.getTown());
        }else {
            AppUser userWhoCreate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            communaute.setCreateBy(userWhoCreate);
            communaute.setUpdateBy(userWhoCreate);
            communaute.setName(communaute.getName().toUpperCase());
            communaute.setTown(communaute.getTown().toUpperCase());
            Communaute createdCommunaute = communauteRepository.save(communaute);

            if(createdCommunaute != null) {
                log.info("Communauté créée avec succès !");
                responseEntity = new ResponseEntity(200, "Communauté créée avec sussès !", "SUCCESS", CommunauteDto.fromEntity(createdCommunaute));
            }else {
                log.error("Une erreur s'est produite lors de la création de la communauté.");
                responseEntity = new ResponseEntity(501, "Une erreur s'est produite lors de la création de la communauté.", "FAILED", null);
            }
        }
        return responseEntity;
    }

    public ResponseEntity updateCommunaute(Communaute communaute) {
        log.info("Modification de la communauté par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity;
        if(communaute == null) {
            throw new InvalidEntityException("La valeur de la variable \"communaute\" est nulle.", ErrorCodes.INVALID_ENTITY);
        }
        communaute.setTown(communaute.getTown().toUpperCase());
        communaute.setName(communaute.getName().toUpperCase());
        Optional<Communaute> comm = communauteRepository.findCommunauteByTown(communaute.getTown());
        if(comm.isPresent()){
            if(comm.get().getIdCom() != communaute.getIdCom()) {
                responseEntity = new ResponseEntity(1000, "Une communauté existe déjà dans la même ville de " + communaute.getTown(), "FAILED", null);
                log.error("Error: Une communauté existe déjà dans la même ville de" + communaute.getTown());
            }else {
                Communaute communauteToUpdate = communauteRepository.findCommunauteByIdCom(communaute.getIdCom()).get();
                communaute.setCreateBy(communauteToUpdate.getCreateBy());
                AppUser userWhoUpdate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
                communaute.setUpdateBy(userWhoUpdate);
                communaute.setCreationDate(communauteToUpdate.getCreationDate());

                communauteToUpdate =  communauteRepository.save(communaute);
                log.info("Communauté mise à jour avec succès !");
                responseEntity = new ResponseEntity(200, "Communauté mis à jour avec succès !", "SUCCESS", CommunauteDto.fromEntity(communauteToUpdate));
            }
        }else {
            Communaute communauteToUpdate = communauteRepository.findCommunauteByIdCom(communaute.getIdCom()).get();
            if(communauteToUpdate != null) {
                communaute.setCreateBy(communauteToUpdate.getCreateBy());
                AppUser userWhoUpdate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
                communaute.setUpdateBy(userWhoUpdate);
                communaute.setCreationDate(communauteToUpdate.getCreationDate());
                communauteToUpdate =  communauteRepository.save(communaute);
                log.info("Communauté mise à jour avec succès !");
                responseEntity = new ResponseEntity(200, "Communauté mis à jour avec succès !", "SUCCESS", CommunauteDto.fromEntity(communauteToUpdate));
            }else {
                responseEntity = new ResponseEntity(1002, "La communauté que vous voulez modifier n'existe pas.", "FAILED", null);
                log.error("Error: La communauté que vous voulez modifier n'existe pas.");
            }

        }

        return responseEntity;
    }

    public ResponseEntity getCommunauteById(Long idCommunaute) {
        log.info("Recherche de la communaute d'ID " + idCommunaute + " par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());

        /*if(idAssociation == null) {
            responseEntity.setCode(1001);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("L'identifiant de l'association ne doit pas être nul !");
            responseEntity.setValue(null);
            log.error("FAILLED: L'identifiant de l'association est nul.");
            return responseEntity;
        }
        */
        Optional<Communaute> communaute = communauteRepository.findById(idCommunaute);
        ResponseEntity responseEntity = new ResponseEntity();
        if(communaute.isPresent()) {
            responseEntity.setCode(200);
            responseEntity.setStatus("SUCCESS");
            responseEntity.setMessage("Recherche fructueuse !");
            responseEntity.setValue(CommunauteDto.fromEntity(communaute.get()));
            log.info("SUCCESS: Communauté trouvée !");
        }else {
            responseEntity.setCode(1002);
            responseEntity.setStatus("SUCCESS");
            responseEntity.setMessage("Aucune communauté n'existe avec l'identifiant fourni");
            responseEntity.setValue(null);
            log.info("SUCCESS: Aucune communauté n'existe avec l'identifiant fourni.");
        }

        return responseEntity;
    }

    public ResponseEntity getAllCommunaute() {
        log.info("Recherche de la liste des communautés par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());

        List<Communaute> communautes = communauteRepository.findAll();
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(200);
        responseEntity.setStatus("SUCCESS");
        if(communautes.isEmpty()) {
            responseEntity.setMessage("La liste des communauté est vide.");
            log.info("SUCCESS: Aucune communauté n'est enregistrée.");
        }else {
            responseEntity.setMessage("Liste des communautés.");
            log.info("SUCCESS: Communauté(s) trouvée(s) !");
        }


        responseEntity.setValue(
                communautes.stream()
                        .map(communaute -> CommunauteDto.fromEntity(communaute))
                        .collect(Collectors.toList())
        );
        return responseEntity;
    }


}
