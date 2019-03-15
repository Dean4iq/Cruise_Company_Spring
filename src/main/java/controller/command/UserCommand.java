package controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UserCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(UserCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/user/home.jsp";
    }
}
