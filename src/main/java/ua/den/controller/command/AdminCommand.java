package ua.den.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code AdminCommand} provide method to provide page link to admin home page
 *
 * @author Dean4iq
 * @version 1.0
 */
@Component
@SessionScope
public class AdminCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AdminCommand.class);
    private static final String ADMIN_HOMEPAGE_JSP = "admin/admin_homepage";

    /**
     * Returns link to the admin home page
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the admin homepage
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");
        return ADMIN_HOMEPAGE_JSP;
    }
}
