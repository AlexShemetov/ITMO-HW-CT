package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.TalksRepository;
import ru.itmo.wp.model.repository.impl.TalksRepositoryImpl;

import java.util.List;

public class TalksService {
    private final TalksRepository talksService = new TalksRepositoryImpl();
    public void save(Message message) {
        talksService.save(message);
    }

    public List<Message> findAll() {
        return talksService.findAll();
    }

    public void validateMessage(Message message) throws ValidationException {
        if (message.getText().length() > 10000) {
            throw new ValidationException("To long text.");
        }

        if (Strings.isNullOrEmpty(message.getText()) || message.getText().matches("\s*")) {
            throw new ValidationException("Text is required");
        }
    }
}
