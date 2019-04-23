package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.exception.NoSuchIdException;
import ua.den.model.service.TicketService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/ticket")
public class AdminSearchController {
    private static final Logger LOG = LogManager.getLogger(AdminSearchController.class);
    private static final String ADMIN_SEARCH_PAGE_JSP = "admin/search";

    @Autowired
    private TicketService ticketService;

    @GetMapping("")
    public String getPage() {
        return ADMIN_SEARCH_PAGE_JSP;
    }

    @PostMapping("")
    public String processRequest(@RequestParam("ticketId") Long ticketId,
                                 HttpServletRequest request) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            request.setAttribute("foundedTicket", ticket);
        } catch (NoSuchIdException e) {
            LOG.error("Ticket {} not found", ticketId);
            request.setAttribute("ticketNotFound", true);
        }

        return ADMIN_SEARCH_PAGE_JSP;
    }
}
