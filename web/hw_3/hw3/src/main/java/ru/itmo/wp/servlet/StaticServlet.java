package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] uris = request.getRequestURI().split("\\+");
        String pathProject = "C:\\Users\\alexs\\Documents\\code\\web\\hw_3\\hw3\\";
        String type = null;
        for (String uri : uris) {
            File file = new File(pathProject + "src/main/webapp/static/" + uri);
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("static/" + uri));
            }

            if (file.isFile()) {
                if (type == null) {
                    type = getServletContext().getMimeType(file.getName());
                    response.setContentType(type);
                }
                try (OutputStream outputStream = response.getOutputStream()) {
                    Files.copy(file.toPath(), outputStream);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
