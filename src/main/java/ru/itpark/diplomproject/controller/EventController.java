package ru.itpark.diplomproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itpark.diplomproject.service.EventService;

@Controller
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public String getAllEvents(Model model){
        model.addAttribute("events",eventService.findAllEvents());
        return "events";
    }

}
