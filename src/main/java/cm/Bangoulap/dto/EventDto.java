package cm.Bangoulap.dto;

import cm.Bangoulap.entities.Event;
import cm.Bangoulap.user.appuser.AppUser;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class EventDto {

    private Long idEvent;
    private String name;
    private int eventYear;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifDate;
    private AppUser createBy;
    private AppUser updateBy;
    private boolean deleted = false;
    private boolean closed = false;

    public static EventDto fromEntity(Event event) {
        if(event == null) {
            return null;
            // TODO: throw an exception
        }

        return EventDto.builder()
                .idEvent(event.getIdEvent())
                .name(event.getName())
                .eventYear(event.getEventYear())
                // .creationDate(event.getCreationDate())
                // .lastModifDate(event.getLastModifDate())
                // .createBy(event.getCreateBy())
                // .updateBy(event.getUpdateBy())
                .closed(event.isClosed())
                .build();
    }

    public static Event toEntity(EventDto eventDto) {
        if(eventDto == null) {
            return null;
            // TODO: throw an exception
        }

        Event event = new Event();
        event.setIdEvent(eventDto.getIdEvent());
        event.setName(eventDto.getName());
        event.setEventYear(eventDto.getEventYear());

        return event;
    }
}
