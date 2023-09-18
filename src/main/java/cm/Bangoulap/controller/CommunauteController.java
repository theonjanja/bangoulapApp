package cm.Bangoulap.controller;

import cm.Bangoulap.controller.api.CommunauteApi;
import cm.Bangoulap.dto.CommunauteDto;
import cm.Bangoulap.entities.Communaute;
import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.service.CommunauteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static cm.Bangoulap.utils.Constants.APP_ROOT;


@RestController
// @RequestMapping( APP_ROOT + "/communaute")
public class CommunauteController implements CommunauteApi {

    private final CommunauteService communauteService;

    public CommunauteController(CommunauteService communauteService) {
        this.communauteService = communauteService;
    }

    // @JsonIgnore
    /*public ResponseEntity createCommunaute(@RequestBody Communaute communaute) {
        Communaute createdCommunaute =  communauteService.saveCommunaute(communaute);
        log.error("Retour: {}", createdCommunaute);
        // return ResponseEntity.ok(createdCommunaute);
        if(createdCommunaute != null) {
            log.error("Position 1.");
            return new ResponseEntity(200, "Communauté créée avec sussès.", "SUCCESS", createdCommunaute);
        }else {
            log.error("Position 2.");
            return new ResponseEntity(501, "Une erreur s'est produite lors de la création de la communauté.", "FAILED", new ResponseEntity());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Communaute> getAllCommunaute() {
        return communauteService.getAllCommunaute();
    }

    @GetMapping(value = "/{idCummunaute}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Communaute getCommunauteById(@PathVariable("idcummaunaute") Long id) {
        Communaute communaute =  communauteService.getCommunauteById(id);

        return Optional.of(communaute).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune communauté n'a été retrouvée avec l'ID " + id
                ));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Communaute updteCommunaute(@RequestBody Communaute communaute) {
        return communauteService.saveCommunaute(communaute);
    }*/

    @Override
    public ResponseEntity save(CommunauteDto communauteDto) {
        return communauteService.saveCommunaute(CommunauteDto.toEntity(communauteDto));
    }

    @Override
    public ResponseEntity update(CommunauteDto communauteDto) {
        return communauteService.updateCommunaute(CommunauteDto.toEntity(communauteDto));
    }

    @Override
    public ResponseEntity getById(Long id) {
        return communauteService.getCommunauteById(id);
    }

    @Override
    public ResponseEntity getAll() {
        return communauteService.getAllCommunaute();
    }
}
