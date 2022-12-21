package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class LogoutPage extends Page {
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        Event event = new Event();
        event.setType(Event.Types.LOGOUT);
        User user = getUser();
        event.setUserId(user.getId());
        eventService.save(event);
        request.getSession().removeAttribute("user");
        setMessage("Good bye. Hope to see you soon!");

        throw new RedirectException("/index");
    }
}
