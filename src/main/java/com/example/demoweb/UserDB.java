package com.example.demoweb;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class UserDB {
    public static UserDB userDB = new UserDB();
    private final Map<String, User> usersByLogin = new HashMap<>();
    private final Map<String, User> userBySession = new HashMap<>();


    public User getUserByCookies(Cookie[] cookies) {
        String session = CookieUtil.getValue(cookies, "JSESSIONID");
        User user = userBySession.get(session);
        if (session == null || user == null) {
            return null;
        }

        return user;
    }

    public User getUserByLogin(String login) {
        return usersByLogin.get(login);
    }

    public void addUser(User user) {
        usersByLogin.put(user.getLogin(), user);
    }

    public void addUserBySession(String session, User user) {
        userBySession.put(session, user);
    }

    public void removeUserBySession(String session) {
        userBySession.remove(session);
    }

    public void removeUser(String login) {
        usersByLogin.remove(login);
    }

    public boolean containsUserByLogin(String login) {
        return usersByLogin.containsKey(login);
    }
}
