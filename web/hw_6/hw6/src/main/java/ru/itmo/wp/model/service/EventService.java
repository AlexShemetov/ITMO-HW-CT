package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventService = new EventRepositoryImpl();
    public void save(Event event) {
        eventService.save(event);
    }
}
