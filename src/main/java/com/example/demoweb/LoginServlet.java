package com.example.demoweb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserDB.userDB.getUserByCookies(req.getCookies());

        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            return;
        }

        User user = UserDB.userDB.getUser(login);
        if (user == null || !user.getPassword().equals(password)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        CookieUtil.addCookie(resp, "login", login);
        CookieUtil.addCookie(resp, "password", password);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
