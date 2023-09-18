package cm.Bangoulap.controller;

import cm.Bangoulap.controller.api.AssociationApi;
import cm.Bangoulap.dto.AssociationDto;
import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.service.AssociationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssociationController implements AssociationApi {

    private final AssociationService associationService;

    public AssociationController(AssociationService associationService) {
        this.associationService = associationService;
    }

    @Override
    public ResponseEntity save(AssociationDto associationDto) {
        return associationService.saveAssociation(AssociationDto.toEntity(associationDto));
    }

    @Override
    public ResponseEntity update(AssociationDto associationDto) {
        return associationService.updateAssociation(AssociationDto.toEntity(associationDto));
    }

    @Override
    public ResponseEntity getById(Long id) {
        return associationService.getAssociationById(id);
    }

    @Override
    public ResponseEntity getAll() {
        return associationService.getAllAssociation();
    }

    @Override
    public ResponseEntity getByTown(String town) {
        return associationService.getAssociationByTown(town);
    }

    @Override
    public ResponseEntity getByCommunaute(Long idComm) {
        return associationService.getAssociationByCommunaute(idComm);
    }
}
