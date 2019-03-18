package controller.listener;

import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Set;

import static model.entity.enums.UserType.GUEST;

public class SessionListener implements HttpSessionListener {
    public static final Logger LOG = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ServletContext context = httpSessionEvent.getSession().getServletContext();

        Set<HttpSession> sessionSet = (Set<HttpSession>) context
                .getAttribute("userSession");
        sessionSet.add(httpSessionEvent.getSession());

        httpSessionEvent.getSession().setAttribute("Role", GUEST);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext context = session.getServletContext();

        HashSet<String> loggedUsers = (HashSet<String>) context
                .getAttribute("userList");
        Set<HttpSession> sessionSet = (Set<HttpSession>) context
                .getAttribute("userSession");

        sessionSet.remove(httpSessionEvent.getSession());

        String userName = ((User)session.getAttribute("User")).getLogin();
        loggedUsers.remove(userName);
    }
}
