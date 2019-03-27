package controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class {@code LogoutCommand} provide methods to process logout
 *
 * @author Dean4iq
 * @version 1.0
 */
public class LogoutCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

    /**
     * Process user request to logout
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the login page
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");

        HttpSession session = request.getSession();

        session.invalidate();
        return "redirect: /login";
    }
}
