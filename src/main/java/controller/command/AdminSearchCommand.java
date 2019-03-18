package controller.command;

import javax.servlet.http.HttpServletRequest;

public class AdminSearchCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/admin/search.jsp";
    }
}
