package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);
    private static final String USER_HOMEPAGE_JSP = "user/home";
    private static final String USER_SEARCH_PAGE_JSP = "user/search";
    private static final String TICKETS_PAGE_REDIRECT = "redirect:/user/tickets";

    @GetMapping("")
    public String getHomePage() {
        return USER_HOMEPAGE_JSP;
    }
}
