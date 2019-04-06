package com.autoever.pilot.events;

import com.autoever.pilot.model.User;
import com.autoever.pilot.users.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/api/events")
public class EventController {

    private final ModelMapper modelMapper;

    private final EventValidator eventValidator;

    public EventController(ModelMapper modelMapper, EventValidator eventValidator) {
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto,
                                      Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
//        event.setManager(currentUser);
//        Event newEvent = this.eventRepository.save(event);

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity queryEvents(@CurrentUser User user) {
//        Page<Event> page = this.eventRepository.findAll(pageable);

//        return ResponseEntity.ok(page);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
//        Optional<Event> optionalEvent = this.eventRepository.findById(id);
//        if (!optionalEvent.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Event event = optionalEvent.get();

//        return ResponseEntity.ok(event);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors) {
//        Optional<Event> optionalEvent = this.eventRepository.findById(id);
//        if (!optionalEvent.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        if (errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(errors);
//        }
//
//        this.eventValidator.validate(eventDto, errors);
//        if (errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(errors);
//        }
//
//        Event existingEvent = optionalEvent.get();
////        if (!existingEvent.getManager().equals(currentUser)) {
////            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
////        }
//
//        this.modelMapper.map(eventDto, existingEvent);
//        Event savedEvent = this.eventRepository.save(existingEvent);

//        return ResponseEntity.ok(savedEvent);
        return ResponseEntity.ok(null);
    }

}
