package cm.Bangoulap.entities;

import cm.Bangoulap.user.appuser.AppUser;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Gift {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long idGift;
    private MoyenDePaiement moyenDePaiement;
    @CreatedDate
    @JoinColumn(name = "payment_date", nullable = false, updatable = false)
    private LocalDateTime paymentDate;
    private Long amount;
    @OneToOne
    @JoinColumn(
            name = "idMembre"
    )
    private AppUser membre;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "idAssociation"
    )
    private Association association;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "idEvent"
    )
    private Event event;

    @ManyToOne
    private AppUser payeur;

}
