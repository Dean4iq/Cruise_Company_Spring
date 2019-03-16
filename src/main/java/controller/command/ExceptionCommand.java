package controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ExceptionCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(ExceptionCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");
        return "/error.jsp";
    }
}
