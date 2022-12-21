package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected HttpServletRequest request;

    protected void putUser(Map<String, Object> view) {
        User user = getUser();
        if (user != null) {
            view.put("user", user);
        }
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }

    protected void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("message", message);
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        view.put("userCount", userService.findCount());
        putUser(view);
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
