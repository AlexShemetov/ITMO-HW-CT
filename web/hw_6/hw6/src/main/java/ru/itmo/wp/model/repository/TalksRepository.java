package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Message;

import java.util.List;

public interface TalksRepository {
    void save(Message message);

    List<Message> findAll();
}
