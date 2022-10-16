package com.example.demoweb;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/")
public class FileManagerServlet extends HttpServlet {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    public void destroy() {

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("exit") != null) {
            UserDB.userDB.removeUserBySession(CookieUtil.getValue(req.getCookies(), "JSESSIONID"));
            CookieUtil.addCookie(resp, "JSESSIONID", null);
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserDB.userDB.getUserByCookies(req.getCookies());
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        String path = req.getParameter("path");
        if (path == null || !path.startsWith("C:\\" + user.getLogin() + "\\")) {
            path = "C:\\" + user.getLogin() + "\\";
        }

        path = path.replaceAll("%20", " ");

        File currentPath = new File(path);
        if (!currentPath.exists()) {
            currentPath.mkdir();
        }

        if (currentPath.isDirectory()) {
            viewFiles(req, currentPath);
            req.setAttribute("date", dateFormat.format(new Date()));
            req.setAttribute("currentPath", path);
            req.getRequestDispatcher("fileManager.jsp").forward(req, resp);
        } else {
            downloadFile(resp, currentPath);
        }
    }

    private void downloadFile(HttpServletResponse resp, File file) throws IOException {
        resp.setContentType("text/plain");
        resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

        try (InputStream in = new FileInputStream(file); OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[1048];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }

    private void viewFiles(HttpServletRequest req, File currentPath) {
        File[] allFiles = currentPath.listFiles();
        if (allFiles == null) {
            return;
        }
        List<File> directories = new ArrayList<>();
        List<File> files = new ArrayList<>();
        for (File file : allFiles) {
            (file.isDirectory() ? directories : files).add(file);
        }
        req.setAttribute("files", files);
        req.setAttribute("directories", directories);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
