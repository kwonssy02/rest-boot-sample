package com.autoever.pilot.events;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;

    private final EventValidator eventValidator;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto,
                                      Errors errors) {
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
//        event.setManager(currentUser);
        Event newEvent = this.eventRepository.save(event);

        return ResponseEntity.ok(newEvent);
    }

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable,
                                      PagedResourcesAssembler<Event> assembler) {
        Page<Event> page = this.eventRepository.findAll(pageable);

//        if (account != null) {
//            pagedResources.add(linkTo(EventController.class).withRel("create-event"));
//        }
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Event event = optionalEvent.get();

        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (!optionalEvent.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        this.eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Event existingEvent = optionalEvent.get();
//        if (!existingEvent.getManager().equals(currentUser)) {
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        }

        this.modelMapper.map(eventDto, existingEvent);
        Event savedEvent = this.eventRepository.save(existingEvent);

        return ResponseEntity.ok(savedEvent);
    }

    private ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(errors);
    }

}