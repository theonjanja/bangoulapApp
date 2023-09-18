package cm.Bangoulap.dto;

import cm.Bangoulap.entities.Communaute;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class CommunauteDto {

    private Long idCom;
    private String name;
    private String town;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifDate;
    private AppUserDto createBy;
    private AppUserDto updateBy;
    private AppUserDto chefCom;
    private Set<AssociationDto> associations;
    private boolean deleted = false;

    public static CommunauteDto fromEntity(Communaute communaute) {
        if(communaute == null) {
            return null;
            // TODO: throw an exception
        }

        return CommunauteDto.builder()
                .idCom(communaute.getIdCom())
                .name(communaute.getName())
                .town(communaute.getTown())
                .creationDate(communaute.getCreationDate())
                .lastModifDate(communaute.getLastModifDate())
                .createBy(AppUserDto.fromEntity(communaute.getCreateBy()))
                .updateBy(AppUserDto.fromEntity(communaute.getUpdateBy()))
                .chefCom(AppUserDto.fromEntity(communaute.getChefCom()))
                .build();
    }

    public static Communaute toEntity(CommunauteDto communauteDto) {
        if(communauteDto == null) {
            return null;
            // TODO: throw an exception
        }

        Communaute communaute = new Communaute();
        communaute.setIdCom(communauteDto.getIdCom());
        communaute.setName(communauteDto.getName());
        communaute.setTown(communauteDto.getTown());
        // communaute.setCreationDate(communauteDto.getCreationDate());
        // communaute.setLastModifDate(communauteDto.getLastModifDate());
        // communaute.setCreateBy(communauteDto.getCreateBy());
        // communaute.setUpdateBy(communauteDto.getUpdateBy());
        communaute.setChefCom(AppUserDto.toEntity(communauteDto.getChefCom()));
        // communaute.setAssociations(AssociationDto.toEntity(communauteDto.getAssociations()));

        return communaute;
    }

}
