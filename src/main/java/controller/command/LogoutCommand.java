package controller.command;

import model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class LogoutCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();

        User user = (User) session.getAttribute("User");

        Set<String> loggedUsers = (HashSet<String>) context.getAttribute("userList");

        session.removeAttribute("User");
        loggedUsers.remove(user.getLogin());

        session.invalidate();
        return "redirect: /login";
    }
}
