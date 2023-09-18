package cm.Bangoulap.entities;

import cm.Bangoulap.user.appuser.AppUser;
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
public class Communaute {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
            // generator = "student_sequence"
    )
    private Long idCom;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String town;

    @CreatedDate
    @JoinColumn(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastModifDate;

    @ManyToOne
    @JoinColumn(
            name = "user_creation_id"
    )
    @JsonIgnore
    private AppUser createBy;
    @ManyToOne
    @JoinColumn(
            name = "last_user_update_id"
    )
    @JsonIgnore
    private AppUser updateBy;

    @OneToOne
    @JoinColumn(
            name = "chairman_user_id"
    )
    private AppUser chefCom;

    @OneToMany(mappedBy = "communaute")
    private Set<Association> associations;

    private boolean deleted = false;

}
