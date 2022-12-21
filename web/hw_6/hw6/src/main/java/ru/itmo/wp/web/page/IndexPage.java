package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.impl.BasicRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class IndexPage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }
}
