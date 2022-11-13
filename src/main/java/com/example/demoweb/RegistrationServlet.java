package com.example.demoweb;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    public void destroy() {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserDB.userDB.getUserByCookies(req.getCookies());

        if (user != null) {
            resp.sendRedirect("/");
            return;
        }

        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (login == null || UserDB.userDB.getUser(login) != null || email == null || password == null) {
            return;
        }

        User user = new User(login, email, password);
        UserDB.userDB.addUser(user);
        CookieUtil.addCookie(resp, "login", login);
        CookieUtil.addCookie(resp, "password", password);
        resp.sendRedirect("/");
    }
}
