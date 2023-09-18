package cm.Bangoulap.dto;

import cm.Bangoulap.entities.Association;
import cm.Bangoulap.user.appuser.AppUser;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class AssociationDto {

    private Long idAss;
    private String name;
    private String town;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifDate;
    private AppUserDto createBy;
    private AppUserDto updateBy;
    private AppUserDto president;
    private CommunauteDto communaute;
    private boolean deleted = false;
    private Set<AppUser> membre;

    public static AssociationDto fromEntity(Association association) {
        if(association == null) {
            return null;
            // TODO: throw an exception
        }

        return AssociationDto.builder()
                .idAss(association.getIdAss())
                .name(association.getName())
                .town(association.getTown())
                .creationDate(association.getCreationDate())
                .lastModifDate(association.getLastModifDate())
                .createBy(AppUserDto.fromEntity(association.getCreateBy()))
                .updateBy(AppUserDto.fromEntity(association.getUpdateBy()))
                .president(AppUserDto.fromEntity(association.getPresident()))
                .communaute(CommunauteDto.fromEntity(association.getCommunaute()))
                // .deleted(association.isDeleted())
                .build();
    }

    public static Association toEntity(AssociationDto associationDto) {
        if(associationDto == null) {
            return null;
            // TODO: throw an exception
        }

        Association association = new Association();
        association.setIdAss(associationDto.getIdAss());
        association.setName(associationDto.getName());
        association.setTown(associationDto.getTown());
        // association.setPresident(AppUserDto.toEntity(associationDto.getPresident()));
        // association.setCommunaute(CommunauteDto.toEntity(associationDto.getCommunaute()));

        return association;
    }
}
