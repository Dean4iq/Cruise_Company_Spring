package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.den.model.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
public class GuestController {
    private static final Logger LOG = LogManager.getLogger(GuestController.class);
    private static final String LOGIN_PAGE_JSP = "login";
    private static final String ADMIN_PAGE_REDIRECT = "redirect:/admin";
    private static final String USER_PAGE_REDIRECT = "redirect:/user";

    private UserRepository userRepository;

    @RequestMapping(value = {"", "/"})
    public String setStartPage() {
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        if (roles.stream().anyMatch(role -> role.equals("ROLE_ADMIN"))) {
            return ADMIN_PAGE_REDIRECT;
        } else if (roles.stream().anyMatch(role -> role.equals("ROLE_USER"))) {
            return USER_PAGE_REDIRECT;
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String setloginPage() {
        return LOGIN_PAGE_JSP;
    }

    @RequestMapping("/register")
    public String processRegister(HttpServletRequest request) {
        return null;
    }
}
