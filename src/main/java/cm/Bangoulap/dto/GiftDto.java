package cm.Bangoulap.dto;

import cm.Bangoulap.entities.Association;
import cm.Bangoulap.entities.Event;
import cm.Bangoulap.entities.Gift;
import cm.Bangoulap.entities.MoyenDePaiement;
import cm.Bangoulap.user.appuser.AppUser;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@Builder
public class GiftDto {

    private Long idGift;
    private MoyenDePaiement moyenDePaiement;
    private LocalDateTime paymentDate;
    private Long amount;
    private AppUserDto membre;
    private AssociationDto association;
    private EventDto event;

    public static GiftDto fromEntity(Gift gift) {

        if(gift == null) {
            return null;
            // TODO: throw an exception
        }

        return GiftDto.builder()
                .idGift(gift.getIdGift())
                .moyenDePaiement(gift.getMoyenDePaiement())
                .paymentDate(gift.getPaymentDate())
                .amount(gift.getAmount())
                .membre(AppUserDto.fromEntity(gift.getMembre()))
                .association(AssociationDto.fromEntity(gift.getAssociation()))
                .event(EventDto.fromEntity(gift.getEvent()))

                .build();
    }

    public static Gift toEntity(GiftDto giftDto) {
        if(giftDto == null) {
            return null;
            // TODO: throw an exception
        }

        Gift gift = new Gift();
        gift.setIdGift(giftDto.getIdGift());
        gift.setMoyenDePaiement(giftDto.getMoyenDePaiement());
        gift.setPaymentDate(giftDto.getPaymentDate());
        gift.setAmount(giftDto.getAmount());
        gift.setMembre(AppUserDto.toEntity(giftDto.getMembre()));
        gift.setAssociation(AssociationDto.toEntity(giftDto.getAssociation()));
        gift.setEvent(EventDto.toEntity(giftDto.getEvent()));

        return gift;
    }

}
