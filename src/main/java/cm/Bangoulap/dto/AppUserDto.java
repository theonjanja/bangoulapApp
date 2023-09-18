package cm.Bangoulap.dto;

import cm.Bangoulap.user.appuser.AppUser;
import cm.Bangoulap.user.appuser.AppUserRole;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class AppUserDto {

    private Long idUser;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private String residence;
    private int yearOfBirth;
    private String title;
    private String country;
    private AppUserRole appUserRole;
    private Boolean deleted = false;
    private Instant createdDate;
    private LocalDateTime lastModifiedDate;
    private AppUserDto updateBy;
    private Boolean locked = false;
    private Boolean enabled = false;
    private Set<AssociationDto> associations;

    public static AppUserDto fromEntity(AppUser appUser) {
        if(appUser == null) {
            return null;
            // TODO throw an exception
        }

        return AppUserDto.builder()
                .idUser(appUser.getIdUser())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .phoneNumber(appUser.getPhoneNumber())
                .gender(appUser.getGender())
                .residence(appUser.getResidence())
                .yearOfBirth(appUser.getYearOfBirth())
                .title(appUser.getTitle())
                .country(appUser.getCountry())
                .appUserRole(appUser.getAppUserRole())
                /*.deleted(appUser.getDeleted())
                .createdDate(appUser.getCreatedDate())
                .lastModifiedDate(appUser.getLastModifiedDate())
                .updateBy(AppUserDto.fromEntity(appUser.getUpdateBy()))
                .locked(appUser.getLocked())
                .enabled(appUser.getEnabled())
                .associations(AssociationDto.fromEntity(appUser.getAssociations()));*/
                .build();
    }

    public static AppUser toEntity(AppUserDto appUserDto) {
        if(appUserDto == null) {
            return null;
            // TODO: throw an exception
        }

        AppUser appUser = new AppUser();
        appUser.setIdUser(appUserDto.getIdUser());
        appUser.setFirstName(appUserDto.getFirstName());
        appUser.setLastName(appUserDto.getLastName());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setPassword(appUserDto.getPassword());
        appUser.setPhoneNumber(appUserDto.getPhoneNumber());
        appUser.setGender(appUserDto.getGender());
        appUser.setResidence(appUserDto.getResidence());
        appUser.setYearOfBirth(appUserDto.getYearOfBirth());
        appUser.setTitle(appUserDto.getTitle());
        appUser.setCountry(appUserDto.getCountry());
        appUser.setAppUserRole(appUserDto.getAppUserRole());
        // appUser.setDeleted(appUserDto.getDeleted());
        // appUser.setLocked(appUserDto.getLocked());
        // appUser.setEnabled(appUserDto.getEnabled());
        // appUser.setAssociations();

        return appUser;
    }
}
