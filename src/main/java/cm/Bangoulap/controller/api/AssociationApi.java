package cm.Bangoulap.controller.api;

import cm.Bangoulap.dto.AssociationDto;
import cm.Bangoulap.entities.Association;
import cm.Bangoulap.entities.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static cm.Bangoulap.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/associations")
public interface AssociationApi {

    @PostMapping(value = APP_ROOT + "/associations/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Créer une association", notes = "Cette méthode permet de créer une association.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'association a été créée avec succès."),
            @ApiResponse(code = 1000, message = "Une association de même nom existe déjà dans la même ville."),
            @ApiResponse(code = 501, message = "Une erreur s'est produite lors de la création de l'association.")
    })
    ResponseEntity save(@RequestBody AssociationDto associationDto);

    @PutMapping(value = APP_ROOT + "/associations/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une association", notes = "Cette méthode permet de mettre à jour une association.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'association a été mise à jour avec succès."),
            @ApiResponse(code = 1000, message = "Une association de même nom existe déjà dans la même ville."),
            @ApiResponse(code = 1002, message = "L'association que vous voulez modifier n'existe pas."),
            @ApiResponse(code = 501, message = "Une erreur s'est produite lors de la mise à jour de l'association.")
    })
    ResponseEntity update(@RequestBody AssociationDto associationDto);

    @GetMapping(value = APP_ROOT + "/associations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche d'une association.", notes = "Cette méthode permet de rechercher une association à partir de son ID.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'association recherchée a été trouvée."),
            @ApiResponse(code = 1002, message = "Aucune association n'existe avec l'identifiant fourni")
    })
    ResponseEntity getById(@PathVariable Long id);

    @GetMapping(value = APP_ROOT + "/associations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche de la liste des associations.", notes = "Cette méthode permet de rechercher la liste des associations.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste a été trouvée. Elle peut être vide ou non.")
    })
    ResponseEntity getAll();

    @GetMapping(value = APP_ROOT + "/associations/town/{town}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche de la liste des associations.", notes = "Cette méthode permet de rechercher la liste des associations d'unne ville donnée.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste a été trouvée. Elle peut être vide ou non.")
    })
    ResponseEntity getByTown(@PathVariable String town);

    @GetMapping(value = APP_ROOT + "/associations/communaute/{idComm}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche de la liste des associations.", notes = "Cette méthode permet de rechercher la liste des associations d'une ville donnée.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste a été trouvée. Elle peut être vide ou non.")
    })
    ResponseEntity getByCommunaute(@PathVariable Long idComm);
}
