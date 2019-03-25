package controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code ExceptionCommand} provide link to the error page
 *
 * @author Dean4iq
 * @version 1.0
 */
public class ExceptionCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ExceptionCommand.class);

    /**
     * Returns link to the error page
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the error page
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");
        return "/error.jsp";
    }
}
