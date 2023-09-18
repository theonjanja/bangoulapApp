package cm.Bangoulap.controller.api;

import cm.Bangoulap.dto.EventDto;
import cm.Bangoulap.entities.Association;
import cm.Bangoulap.entities.Event;
import cm.Bangoulap.entities.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static cm.Bangoulap.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/events")
public interface EventApi {


    @PostMapping(value = APP_ROOT + "/events/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Créer un événement", notes = "Cette méthode permet de créer un événement.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'événement a été créé avec succès."),
            @ApiResponse(code = 1000, message = "Un événement de même nom existe déjà."),
            @ApiResponse(code = 501, message = "Une erreur s'est produite lors de la création de l'événement.")
    })
    ResponseEntity save(@RequestBody EventDto eventDto);

    @PutMapping(value = APP_ROOT + "/events/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un événement", notes = "Cette méthode permet de mettre à jour un événement.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'événement a été mis à jour avec succès."),
            @ApiResponse(code = 1000, message = "Un événement de même nom existe déjà."),
            @ApiResponse(code = 1002, message = "L'événement que vous voulez modifier n'existe pas."),
            @ApiResponse(code = 501, message = "Une erreur s'est produite lors de la mise à jour de l'événement.")
    })
    ResponseEntity update(@RequestBody EventDto eventDto);

    @GetMapping(value = APP_ROOT + "/events/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche d'un événement.", notes = "Cette méthode permet de rechercher un événement à partir de son ID.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'événement recherché a été trouvé."),
            @ApiResponse(code = 1002, message = "Aucun événement n'existe avec l'identifiant fourni")
    })
    ResponseEntity getById(@PathVariable Long id);

    @GetMapping(value = APP_ROOT + "/events", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche de la liste des événements.", notes = "Cette méthode permet de rechercher la liste des événements.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste a été trouvée. Elle peut être vide ou non.")
    })
    ResponseEntity getAll();

    @GetMapping(value = APP_ROOT + "/events/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche de la liste des événements.", notes = "Cette méthode permet de rechercher la liste des événements d'une année donnée.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste a été trouvée. Elle peut être vide ou non.")
    })
    ResponseEntity getByYear(@PathVariable int year);

}
