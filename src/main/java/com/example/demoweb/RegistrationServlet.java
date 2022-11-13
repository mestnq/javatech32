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
        User user = null;
        try {
            user = UserDB.userDB.getUserByCookies(req.getCookies());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

        try {
            if (login == null || UserDB.userDB.containsUserByLogin(login) || email == null || password == null) {
                return;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        User user = new User(login, email, password);
        try {
            UserDB.userDB.addUser(user);
            UserDB.userDB.addUserBySession(CookieUtil.getValue(req.getCookies(), "JSESSIONID"), user);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/");
    }
}
