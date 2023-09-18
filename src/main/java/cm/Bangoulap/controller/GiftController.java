package cm.Bangoulap.controller;

import cm.Bangoulap.controller.api.GiftApi;
import cm.Bangoulap.entities.GiftRequest;
import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.service.GiftService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GiftController implements GiftApi {

    private final GiftService giftService;

    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public ResponseEntity save(GiftRequest giftRequest) {
        return giftService.saveEvent(giftRequest);
    }

    @Override
    public ResponseEntity getById(Long id) {
        return giftService.getGiftById(id);
    }
}
