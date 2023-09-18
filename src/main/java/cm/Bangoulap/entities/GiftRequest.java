package cm.Bangoulap.entities;

import cm.Bangoulap.user.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GiftRequest {
    private Long idMembre; // Celui pour qui le don est fait.
    private Long idAssociation; // Association concerné par le don.
    private Long idEvent; // Evénement concerné par le don.
    private Long idPayeur; // Celui qui effectue le paiement (l'utilisateur connecté).
    private MoyenDePaiement moyenDePaiement;
    private Long amount;
}
