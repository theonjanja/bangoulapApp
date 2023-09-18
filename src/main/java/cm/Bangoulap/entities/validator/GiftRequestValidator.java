package cm.Bangoulap.entities.validator;

import cm.Bangoulap.entities.GiftRequest;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GiftRequestValidator {

    public static List<String> validate(GiftRequest giftRequest) {
        List<String> errors = new ArrayList<>();

        if(giftRequest == null) {
            errors.add("L'identifiant du membre pour qui le don est fait est obligatoire.");
            errors.add("L'identifiant de l'événement est obligatoire.");
            errors.add("L'identifiant du payeur est obligatoire.");
            errors.add("Le moyen de paiement est obligatoire.");
            errors.add("Le montant du don est obligatoire.");
            return errors;
        }
        if(giftRequest.getIdMembre() == null) {
            errors.add("L'identifiant du membre pour qui le don est fait est obligatoire.");
        }
        if(giftRequest.getIdEvent() == null) {
            errors.add("L'identifiant de l'événement est obligatoire.");
        }
        if(giftRequest.getIdPayeur() == null) {
            errors.add("L'identifiant du payeur est obligatoire.");
        }
        if(giftRequest.getMoyenDePaiement() == null) {
            errors.add("Le moyen de paiement est obligatoire.");
        }
        if(giftRequest.getAmount() == null) {
            errors.add("Le montant du don est obligatoire.");
        }
        if(giftRequest.getAmount() <= 0) {
            errors.add("Le montant est invalide.");
        }

        return errors;
    }
}
