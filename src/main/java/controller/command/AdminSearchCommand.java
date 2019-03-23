package controller.command;

import exception.NoSuchIdException;
import model.entity.dto.Ticket;
import model.service.AdminSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AdminSearchCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AdminSearchCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("searchSubmit") != null) {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));

            try {
                Ticket ticket = AdminSearchService.INSTANCE.getTicketInfo(ticketId);
                request.setAttribute("foundedTicket", ticket);
            } catch (NoSuchIdException e) {
                LOG.warn(e);
                request.setAttribute("ticketNotFound", true);
            }
        }

        return "/WEB-INF/admin/search.jsp";
    }
}
