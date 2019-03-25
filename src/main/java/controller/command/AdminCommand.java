package controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code AdminCommand} provide method to provide page link to admin home page
 *
 * @author Dean4iq
 * @version 1.0
 */
public class AdminCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AdminCommand.class);

    /**
     * Returns link to the admin home page
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the admin homepage
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");
        return "/WEB-INF/admin/admin_homepage.jsp";
    }
}
