package com.example.demoweb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
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

        if (login == null || UserDB.userDB.containsUserByLogin(login) || email == null || password == null) {
            return;
        }

        User user = new User(login, email, password);
        UserDB.userDB.addUser(user);
        UserDB.userDB.addUserBySession(CookieUtil.getValue(req.getCookies(), "JSESSIONID"), user);
        resp.sendRedirect("/");
    }
}
