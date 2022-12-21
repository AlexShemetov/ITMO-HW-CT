package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class EnterPage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
    }

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        userService.validateEnter(loginOrEmail, password);
        User user = userService.findByLoginOrEmailAndPassword(loginOrEmail, password);
        request.getSession().setAttribute("user", user);

        Event event = new Event();
        event.setType(Event.Types.ENTER);
        event.setUserId(user.getId());
        eventService.save(event);
        request.getSession().setAttribute("event", event);

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("message", "Hello, " + user.getLogin());

        throw new RedirectException("/index");
    }
}
