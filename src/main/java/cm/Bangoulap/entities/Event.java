package cm.Bangoulap.entities;

import cm.Bangoulap.user.appuser.AppUser;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
public class Event {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long idEvent;

    private String name;

    private int eventYear;

    @CreatedDate
    @JoinColumn(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    private LocalDateTime lastModifDate;

    @OneToOne
    @JoinColumn(
            name = "user_creation_id"
    )
    private AppUser createBy;
    @OneToOne
    @JoinColumn(
            name = "last_user_update_id"
    )
    private AppUser updateBy;

    private boolean deleted = false;

    private boolean closed = false;

}
