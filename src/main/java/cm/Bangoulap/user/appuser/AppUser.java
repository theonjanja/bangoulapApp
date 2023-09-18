package cm.Bangoulap.user.appuser;

import cm.Bangoulap.entities.Association;
import cm.Bangoulap.entities.Communaute;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
// @EqualsAndHashCode
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AppUser implements UserDetails {

    /*@SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )*/
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
            // generator = "student_sequence"
    )
    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    private String gender;
    private String residence;
    private int yearOfBirth;
    private String title;
    private String country;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean deleted = false;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
    @OneToOne
    @JoinColumn(
            name = "last_user_update_id"
    )
    @JsonIgnore
    private AppUser updateBy;
    private Boolean locked = false;
    private Boolean enabled = false;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    /*@JoinTable(name = "adhesion", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "idUser")
    })*/
    @JoinTable(name = "adhesion",
            joinColumns = @JoinColumn(name = "user_id"/*, referencedColumnName = "idUser")*/),
            inverseJoinColumns = @JoinColumn(name = "association_id")
    )
    private Set<Association> associations;

    /*@OneToMany(mappedBy = "createBy")
    private Set<Communaute> communautesCree;*/

    public AppUser(
            String firstName,
            String lastName,
            String email,
            String password,
            AppUserRole appUserRole,
            String country,
            String gender,
            String phoneNumber,
            String residence,
            String title,
            int yearOfBirth
            // LocalDateTime lastModifDate
            ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        this.country = country;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.residence = residence;
        this.title = title;
        this.yearOfBirth = yearOfBirth;
        // this.lastModifDate = lastModifDate;
        /*this.locked = locked;
        this.enabled = enabled;*/
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getCountry() { return country; }
    public String getGender() { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getResidence() { return residence; }
    public String getTitle() { return title; }
    public int getYearOfBirth() { return yearOfBirth; }

    public void setUpdateBy(AppUser userWhoUpdate) {
        updateBy = userWhoUpdate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
