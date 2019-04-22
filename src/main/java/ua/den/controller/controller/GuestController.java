package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.den.model.entity.tables.User;
import ua.den.model.entity.enums.UserType;
import ua.den.model.exception.AlreadyLoggedInException;
import ua.den.model.exception.InvalidLoginOrPasswordException;
import ua.den.model.exception.NotExistedLoginException;
import ua.den.model.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Objects;
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

    private String processLoginRequest(HttpServletRequest request) {
        User user = new User.Builder()
                .login(request.getParameter("loginUser"))
                .password(request.getParameter("passwordUser"))
                .build();

        if (request.getParameter("login") != null && user.getLogin() != null && !user.getLogin().equals("")) {
            try {
                User userFromDB = userRepository.getUserByLogin(user.getLogin());

                if (Objects.isNull(userFromDB)) {
                    throw new NotExistedLoginException();
                }

                if (!(userFromDB.getLogin().equals(user.getLogin())
                        && userFromDB.getPassword().equals(user.getPassword()))) {
                    throw new InvalidLoginOrPasswordException();
                }

                if (!userInSystem(userFromDB, request)) {
                    return processUserInSystem(userFromDB, request);
                }

                throw new AlreadyLoggedInException();
            } catch (NotExistedLoginException e) {
                request.setAttribute("noSuchId", true);
                LOG.warn("Someone tried to log in the system using user login: {}", user.getLogin());
            } catch (InvalidLoginOrPasswordException e) {
                request.setAttribute("invalidLoginOrPassword", true);
                LOG.warn("User with login {} tried to log in the system", user.getLogin());
            } catch (AlreadyLoggedInException e) {
                request.setAttribute("alreadyLoggedIn", true);
                LOG.warn("User with login {} already logged in the system", user.getLogin());
            }
        }

        return LOGIN_PAGE_JSP;
    }

    private boolean userInSystem(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        return userList.stream().anyMatch(username -> user.getLogin().equals(username));
    }

    private String processUserInSystem(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        userList.add(user.getLogin());
        request.getSession().setAttribute("User", user);

        LOG.info("User {} successfully logged in the system", user.getLogin());

        if (user.getAuthority().getRole().toString().equals("ROLE_ADMIN")) {
            request.getSession().setAttribute("Role", UserType.ADMIN);
            return ADMIN_PAGE_REDIRECT;
        } else {
            request.getSession().setAttribute("Role", UserType.USER);
            return USER_PAGE_REDIRECT;
        }
    }
}
