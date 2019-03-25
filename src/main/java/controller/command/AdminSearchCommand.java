package controller.command;

import model.exception.NoSuchIdException;
import model.entity.dto.Ticket;
import model.service.AdminSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code AdminSearchCommand} provide method to search user tickets with detailed data about it
 *
 * @author Dean4iq
 * @version 1.0
 */
public class AdminSearchCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AdminSearchCommand.class);
    private AdminSearchService adminSearchService = AdminSearchService.getInstance();

    /**
     * Returns link to the user search page and provides detailed ticket data on demand
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the search page
     */
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("searchSubmit") != null) {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));

            try {
                Ticket ticket = adminSearchService.getTicketInfo(ticketId);
                request.setAttribute("foundedTicket", ticket);
            } catch (NoSuchIdException e) {
                LOG.warn(e);
                request.setAttribute("ticketNotFound", true);
            }
        }

        return "/WEB-INF/admin/search.jsp";
    }
}
