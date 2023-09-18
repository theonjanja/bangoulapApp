package cm.Bangoulap.controller.api;

import cm.Bangoulap.dto.EventDto;
import cm.Bangoulap.entities.GiftRequest;
import cm.Bangoulap.entities.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static cm.Bangoulap.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/gifts")
public interface GiftApi {

    @PostMapping(value = APP_ROOT + "/gifts/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Créer un don", notes = "Cette méthode permet de créer un don.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le don a été créé avec succès."),
            @ApiResponse(code = 1000, message = "Un don de même nom existe déjà."),
            @ApiResponse(code = 501, message = "Une erreur s'est produite lors de la création du don.")
    })
    ResponseEntity save(@RequestBody GiftRequest giftRequest);

    @GetMapping(value = APP_ROOT + "/gifts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Recherche d'un don.", notes = "Cette méthode permet de rechercher un don à partir de son ID.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le don recherché a été trouvé."),
            @ApiResponse(code = 1002, message = "Aucun don n'existe avec l'identifiant fourni")
    })
    ResponseEntity getById(@PathVariable Long id);
}
