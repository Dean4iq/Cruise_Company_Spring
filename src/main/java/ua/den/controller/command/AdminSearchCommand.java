package ua.den.controller.command;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ua.den.model.entity.dto.Ticket;
import ua.den.model.exception.NoSuchIdException;
import ua.den.model.service.AdminSearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Class {@code AdminSearchCommand} provide method to search user tickets with detailed data about it
 *
 * @author Dean4iq
 * @version 1.0
 */
@Component
@SessionScope
public class AdminSearchCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(AdminSearchCommand.class);

    @Autowired
    private AdminSearchService adminSearchService;

    /**
     * Returns link to the user search page and provides detailed ticket data on demand
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the search page
     */
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("searchSubmit") != null) {
            Long ticketId = Long.parseLong(request.getParameter("ticketId"));

            Ticket ticket = null;
            try {
                ticket = adminSearchService.getTicketInfo(ticketId);
                request.setAttribute("foundedTicket", ticket);
            } catch (NoSuchIdException e) {
                LOG.error(e);
                request.setAttribute("ticketNotFound",true);
            }
        }

        return "admin/search";
    }
}
