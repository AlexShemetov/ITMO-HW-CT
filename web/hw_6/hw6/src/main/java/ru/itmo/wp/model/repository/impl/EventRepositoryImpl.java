package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.util.Map;
import java.util.TreeMap;

public class EventRepositoryImpl extends BasicRepositoryImpl implements EventRepository {
    @Override
    public void save(Event event) {
        Map<String, String> map = new TreeMap<>();
        map.put("`userId`", "?");
        map.put("`type`", "?");
        super.save(event, "Event", map);
    }
}
