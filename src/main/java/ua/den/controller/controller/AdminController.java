package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.den.model.entity.tables.Ticket;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger LOG = LogManager.getLogger(AdminController.class);
    private static final String ADMIN_HOMEPAGE_JSP = "admin/admin_homepage";

    @GetMapping("")
    public String getHomePage() {
        LOG.trace("Execute()");
        return ADMIN_HOMEPAGE_JSP;
    }
}
