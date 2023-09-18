package cm.Bangoulap.controller.api;

import cm.Bangoulap.dto.CommunauteDto;
import cm.Bangoulap.entities.Communaute;
import cm.Bangoulap.entities.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static cm.Bangoulap.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/communautes")
public interface CommunauteApi {

    @PostMapping(value = APP_ROOT + "/communautes/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Créer une communauté", notes = "Cette méthode permet de créer une communauté.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La communauté a été créée avec succès."),
            @ApiResponse(code = 1000, message = "Une communauté existe déjà dans la même ville."),
            @ApiResponse(code = 501, message = "Une erreur s'est produite lors de la création de la communauté.")
    })
    ResponseEntity save(@RequestBody CommunauteDto communauteDto);

    @PutMapping(value = APP_ROOT + "/communautes/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une communauté", notes = "Cette méthode permet de mettre à jour une communauté.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La communauté a été mise à jour avec succès."),
            @ApiResponse(code = 1000, message = "Une communauté existe déjà dans la même ville."),
            @ApiResponse(code = 501, message = "Une erreur s'est produite lors de la mise à jour de la communauté.")
    })
    ResponseEntity update(@RequestBody CommunauteDto communauteDto);

    @GetMapping(value = APP_ROOT + "/communautes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche d'une communauté.", notes = "Cette méthode permet de rechercher une communauté à partir de son ID.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La communauté communauté recherchée a été trouvée."),
            @ApiResponse(code = 1002, message = "Aucune communauté n'existe avec l'identifiant fourni")
    })
    ResponseEntity getById(@PathVariable Long id);

    @GetMapping(value = APP_ROOT + "/communautes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche de la liste des communautés.", notes = "Cette méthode permet de rechercher la liste des communautés.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste a été trouvée. Elle peut être vide ou non.")
    })
    ResponseEntity getAll();

}
