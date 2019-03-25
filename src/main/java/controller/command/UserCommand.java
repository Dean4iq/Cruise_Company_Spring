package controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code UserCommand} provide method to provide page link to users home page
 *
 * @author Dean4iq
 * @version 1.0
 */
public class UserCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(UserCommand.class);

    /**
     * Returns link to the user home page
     *
     * @param request provides user data to process and link to session and context
     * @return link to the users homepage
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");

        return "/WEB-INF/user/home.jsp";
    }
}
