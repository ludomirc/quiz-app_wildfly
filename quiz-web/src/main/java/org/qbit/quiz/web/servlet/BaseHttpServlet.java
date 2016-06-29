package org.qbit.quiz.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by beniamin.czaplicki on 2016-05-16.
 */
public abstract class BaseHttpServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected HttpServletResponse openHtmlPage(HttpServletResponse resp, String pageTitle) throws IOException {
        Writer out = resp.getWriter();
        out.append("<!DOCTYPE html>");
        out.append('\n');
        out.append("<html><head>");
        out.append('\n');
        out.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.append('\n');
        out.append("<title>" + pageTitle + "</title></head>");
        out.append('\n');
        out.append("<body>");
        out.append('\n');
        out.flush();
        return resp;
    }

    protected HttpServletResponse closeHtmlPage(HttpServletResponse resp) throws IOException {
        Writer out = resp.getWriter();
        out.append('\n');
        out.append("</body></html>");
        out.append('\n');
        out.flush();
        return resp;
    }

    protected String getServerUrl(HttpServletRequest req) {
        return req.getServerName() + ":" + req.getServerPort();
    }

    public String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }
}
