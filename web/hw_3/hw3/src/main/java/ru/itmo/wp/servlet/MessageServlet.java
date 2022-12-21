package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class MessageServlet extends HttpServlet {
    private static class Message {
        private final String user;
        private final String text;

        private Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    private ArrayList<Message> messages = new ArrayList <>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        HttpSession session = request.getSession(true);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String json;
        switch (uri) {
            case "/message/auth":
                String user = (String) session.getAttribute("user");
                if (user == null) {
                    user = request.getParameter("user");
                    if (user == null || user.split(" ").length == 0) {
                        user = null;
                    } else {
                        session.setAttribute("user", user);
//                        System.out.println(user);
                    }
                }
                json = new Gson().toJson(user);
                response.getWriter().print(json);
                response.getWriter().flush();
                break;
            case "/message/findAll":
                json = new Gson().toJson(messages);
                System.out.println(json);
                response.getWriter().print(json);
                response.getWriter().flush();
                break;
            case "/message/add":
                messages.add(new Message((String) session.getAttribute("user"), request.getParameter("text")));
                break;

        }
    }
}
