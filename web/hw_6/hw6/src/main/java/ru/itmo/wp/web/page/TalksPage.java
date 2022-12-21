package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalksService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final TalksService talkService = new TalksService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        view.put("talks", talkService.findAll());
    }

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (request.getSession().getAttribute("user") == null) {
            throw new RedirectException("/index");
        }
    }

    private void send(HttpServletRequest request, Map<String, Object> view) {
        String text = request.getParameter("text");
        long targetUserId;
        try {
             targetUserId = Long.parseLong(request.getParameter("targetUser"));
        } catch (NumberFormatException e) {
            throw new RedirectException("/talks");
        }
        Message message = new Message();
        message.setText(text);
        message.setTargetUserId(targetUserId);
        message.setSourceUserId(getUser().getId());

        try {
            talkService.validateMessage(message);
        } catch (ValidationException e) {
            throw new RedirectException("/talks");
        }
        talkService.save(message);

        throw new RedirectException("/talks");
    }
}
