package com.example.demoweb;

import javax.servlet.http.Cookie;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDB {
    public static UserDB userDB = new UserDB();

    public User getUserByCookies(Cookie[] cookies) {
        User user = null;
        if ((user = getUser(CookieUtil.getValue(cookies, "login"))) == null || !user.getPassword().equals(CookieUtil.getValue(cookies, "password"))) {
            return null;
        }

        return user;
    }

    public User getUser(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = session.byNaturalId(User.class).using("login", login).load();
        session.close();
        return user;
    }
    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }
}
