package cm.Bangoulap.service;

import cm.Bangoulap.dto.AssociationDto;
import cm.Bangoulap.entities.Association;
import cm.Bangoulap.entities.Communaute;
import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.exception.ErrorCodes;
import cm.Bangoulap.exception.InvalidEntityException;
import cm.Bangoulap.repository.AssociationRepository;
import cm.Bangoulap.repository.CommunauteRepository;
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
public class AssociationService {
    private final AssociationRepository associationRepository;
    private final CommunauteRepository communauteRepository;
    private final AppUserRepository appUserRepository;

    // ResponseEntity responseEntity;

    public ResponseEntity saveAssociation(Association association) {
        log.info("Création de l'association par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity;
        if(association == null) {
            throw new InvalidEntityException("La valeur de la variable \"association\" est nulle.", ErrorCodes.INVALID_ENTITY);
        }
        association.setName(association.getName().toUpperCase());
        association.setTown(association.getTown().toUpperCase());
        Optional<Association>  assoc = associationRepository.findAssociationByName(association.getName());
        if(assoc.isPresent() && association.getTown().equals(assoc.get().getTown())) {
            responseEntity = new ResponseEntity(1000, "Une association de même nom existe déjà dans la même ville de " + association.getTown(), "FAILED", null);
            log.error("Error: Une association de même nom existe déjà dans la même ville de " + association.getTown());
        }else {
            AppUser userWhoCreate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
            association.setCreateBy(userWhoCreate);
            association.setUpdateBy(userWhoCreate);

            if(association.getPresident() != null) {
                AppUser president = appUserRepository.findById(association.getPresident().getIdUser()).get();
                association.setPresident(president);
            }

            Association createdAssociation = associationRepository.save(association);

            if(createdAssociation != null) {
                log.info("Association créée avec succès !");
                responseEntity = new ResponseEntity(200, "Association créée avec sussès !", "SUCCESS", AssociationDto.fromEntity(createdAssociation));
            }else {
                log.error("Une erreur s'est produite lors de la création de l'association.");
                responseEntity = new ResponseEntity(501, "Une erreur s'est produite lors de la création de l'association.", "FAILED", null);
            }
        }
        return responseEntity;
    }

    public ResponseEntity updateAssociation(Association association) {
        log.info("Modification de l'association par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity;
        if (association == null) {
            throw new InvalidEntityException("La valeur de la variable \"association\" est nulle.", ErrorCodes.INVALID_ENTITY);
        }
        association.setTown(association.getTown().toUpperCase());
        association.setName(association.getName().toUpperCase());
        Optional<Association> ass = associationRepository.findAssociationByName(association.getName());

        if (ass.isPresent()) {
            if (!ass.get().getTown().equals(association.getTown())) {
                responseEntity = new ResponseEntity(1000, "Une association de même nom existe déjà dans la ville de " + association.getTown(), "FAILED", null);
                log.error("Error: Une association de même nom existe déjà dans la ville de" + association.getTown());
            } else {
                Association associationToUpdate = associationRepository.findAssociationByIdAss(association.getIdAss()).get();
                if (associationToUpdate != null) {
                    association.setCreateBy(associationToUpdate.getCreateBy());
                    AppUser userWhoUpdate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
                    association.setUpdateBy(userWhoUpdate);
                    association.setCreationDate(associationToUpdate.getCreationDate());
                    associationToUpdate = associationRepository.save(association);
                    log.info("Association mise à jour avec succès !");
                    responseEntity = new ResponseEntity(200, "Association mise à jour avec succès !", "SUCCESS", AssociationDto.fromEntity(associationToUpdate));
                } else {
                    responseEntity = new ResponseEntity(1002, "L'association que vous voulez modifier n'existe pas.", "FAILED", null);
                    log.error("Error: L'association que vous voulez modifier n'existe pas.");
                }

            }
        } else {
            Association associationToUpdate = associationRepository.findAssociationByIdAss(association.getIdAss()).get();
            if (associationToUpdate != null) {
                association.setCreateBy(associationToUpdate.getCreateBy());
                AppUser userWhoUpdate = appUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
                association.setUpdateBy(userWhoUpdate);
                association.setCreationDate(associationToUpdate.getCreationDate());
                associationToUpdate = associationRepository.save(association);
                log.info("Association mise à jour avec succès !");
                responseEntity = new ResponseEntity(200, "Communauté mis à jour avec succès !", "SUCCESS", AssociationDto.fromEntity(associationToUpdate));
            } else {
                responseEntity = new ResponseEntity(1002, "L'association que vous voulez modifier n'existe pas.", "FAILED", null);
                log.error("Error: L'association que vous voulez modifier n'existe pas.");
            }
        }

        return responseEntity;

    }

    public ResponseEntity getAssociationById(Long idAssociation) {
        log.info("Recherche de l'association d'ID " + idAssociation + " par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());
        ResponseEntity responseEntity = new ResponseEntity();
        /*if(idAssociation == null) {
            responseEntity.setCode(1001);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("L'identifiant de l'association ne doit pas être nul !");
            responseEntity.setValue(null);
            log.error("FAILLED: L'identifiant de l'association est nul.");
            return responseEntity;
        }*/

        Optional<Association> association = associationRepository.findAssociationByIdAss(idAssociation);
        if(association.isPresent()) {
            responseEntity.setCode(200);
            responseEntity.setStatus("SUCCESS");
            responseEntity.setMessage("Recherche fructueuse !");
            responseEntity.setValue(AssociationDto.fromEntity(association.get()));
            log.info("SUCCESS: Association trouvée !");
        }else {
            responseEntity.setCode(1002);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("Aucune association n'existe avec l'identifiant fourni");
            responseEntity.setValue(null);
            log.info("ERROR: Aucune association n'existe avec l'identifiant fourni.");
        }

        return responseEntity;
    }

    public ResponseEntity getAllAssociation() {
        log.info("Recherche de la liste des associations par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());

        List<Association> associations = associationRepository.findAll();
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(200);
        responseEntity.setStatus("SUCCESS");
        if(associations.isEmpty()) {
            responseEntity.setMessage("La liste des associations est vide.");
            log.info("SUCCESS: Aucune association n'est enregistrée.");
        }else {
            responseEntity.setMessage("Liste des associations.");
            log.info("SUCCESS: Association(s) trouvée(s) !");
        }

        responseEntity.setValue(
                associations.stream()
                        .map(association -> AssociationDto.fromEntity(association))
                        .collect(Collectors.toList())
        );
        return responseEntity;
    }

    public ResponseEntity getAssociationByTown(String town) {
        log.info("Recherche de la liste des associations de la ville de " + town + " par [USER] " + SecurityContextHolder.getContext().getAuthentication().getName());

        List<Association> associations = associationRepository.findAssociationByTown(town);
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(200);

        if(associations.isEmpty()) {
            responseEntity.setMessage("La liste des associations est vide.");
            log.info("SUCCESS: Aucune association n'est enregistrée dans la ville de " + town + ".");
        }else {
            responseEntity.setMessage("Liste des associations de la ville " + town + ".");
            log.info("SUCCESS: Association(s) trouvée(s) !");
        }

        responseEntity.setValue(
                associations.stream()
                .map(association -> AssociationDto.fromEntity(association))
                .collect(Collectors.toList())
        );
        return responseEntity;
    }

    public ResponseEntity getAssociationByCommunaute(Long idCom) {
        log.info("Recherche de la liste des associations de la communanaute d'ID " + idCom + " par [USER]" + SecurityContextHolder.getContext().getAuthentication().getName());

        Communaute communaute = communauteRepository.findCommunauteByIdCom(idCom).get();
        ResponseEntity responseEntity = new ResponseEntity();
        if(communaute != null) {
            List<Association> associations = associationRepository.findAssociationByCommunaute(idCom);
            responseEntity.setCode(200);
            if(associations.isEmpty()) {
                responseEntity.setMessage("La liste des associations est vide.");
                log.info("SUCCESS: Aucune association n'est enregistrée dans la communauté de " + communaute.getName() + ".");
            }else {
                responseEntity.setMessage("Liste des associations de la communauté " + communaute.getName() + ".");
                log.info("SUCCESS: Association(s) trouvée(s) !");
            }
            responseEntity.setValue(
                    associations.stream()
                            .map(association -> AssociationDto.fromEntity(association))
                            .collect(Collectors.toList())
            );
        }else {
            responseEntity.setCode(1002);
            responseEntity.setStatus("FAILED");
            responseEntity.setMessage("Aucune communauté n'existe avec l'identifiant fourni");
            responseEntity.setValue(null);
            log.info("ERROR: Aucune communauté n'existe avec l'identifiant fourni.");
        }

        return responseEntity;
    }

}
