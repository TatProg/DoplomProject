package ru.itpark.diplomproject.service;

import org.springframework.stereotype.Service;
import ru.itpark.diplomproject.domain.Event;
import ru.itpark.diplomproject.repository.EventRepository;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAllEvents();
    }
}
