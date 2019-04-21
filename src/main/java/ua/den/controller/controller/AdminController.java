package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.den.model.entity.dto.Ticket;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOG = LogManager.getLogger(AdminController.class);
    private static final String ADMIN_HOMEPAGE_JSP = "admin/admin_homepage";
    private static final String ADMIN_SEARCH_PAGE_JSP = "admin/search";

    @RequestMapping("/")
    public String getHomePage() {
        LOG.trace("Execute()");
        return ADMIN_HOMEPAGE_JSP;
    }

    @RequestMapping("/ticket")
    public String getTicketPage() {
        return ADMIN_SEARCH_PAGE_JSP;
    }

    @RequestMapping("/ticket/{id}")
    public String processRequest(@Param("id") Ticket ticket, HttpServletRequest request) {
        Optional.ofNullable(ticket).ifPresent(tick -> request.setAttribute("foundedTicket", tick));

        request.setAttribute("foundedTicket", ticket);

        return ADMIN_SEARCH_PAGE_JSP;
    }
}
