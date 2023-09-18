package cm.Bangoulap.entities;

import cm.Bangoulap.user.appuser.AppUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Association {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
            // generator = "communaute_sequence"
    )
    private Long idAss;

    private String name;

    private String town;

    @CreatedDate
    @JoinColumn(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastModifDate;

    @OneToOne
    @JoinColumn(
            name = "user_creation_id"
    )
    @JsonIgnore
    private AppUser createBy;

    @OneToOne
    @JoinColumn(
            name = "last_user_update_id"
    )
    @JsonIgnore
    private AppUser updateBy;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(
            name = "id_president"
    )
    private AppUser president;
    @ManyToOne
    @JoinColumn(
            name = "id_communaute"
    )
    private Communaute communaute;

    private boolean deleted = false;

    @ManyToMany(mappedBy = "associations", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<AppUser> membre;
}
