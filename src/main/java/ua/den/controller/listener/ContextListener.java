package ua.den.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class ContextListener implements ServletContextListener {
    public static final Logger LOG = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Set<String> activeUsers = new HashSet<>();
        Set<HttpSession> activeSessions = new HashSet<>();

        servletContextEvent.getServletContext()
                .setAttribute("userList", activeUsers);
        servletContextEvent.getServletContext()
                .setAttribute("userSession", activeSessions);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext()
                .removeAttribute("userList");
        servletContextEvent.getServletContext()
                .removeAttribute("userSession");
    }
}
