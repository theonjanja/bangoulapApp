package cm.Bangoulap.controller;

import cm.Bangoulap.controller.api.EventApi;
import cm.Bangoulap.dto.EventDto;
import cm.Bangoulap.entities.Event;
import cm.Bangoulap.entities.ResponseEntity;
import cm.Bangoulap.service.EventService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController implements EventApi {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public ResponseEntity save(EventDto eventDto) {
        return eventService.saveEvent(EventDto.toEntity(eventDto));
    }

    @Override
    public ResponseEntity update(EventDto eventDto) {
        return eventService.updateEvent(EventDto.toEntity(eventDto));
    }

    @Override
    public ResponseEntity getById(Long id) {
        return eventService.getEventById(id);
    }

    @Override
    public ResponseEntity getAll() {
        return eventService.getAllEvent();
    }

    @Override
    public ResponseEntity getByYear(int year) {
        return eventService.getEventsByYear(year);
    }
}
