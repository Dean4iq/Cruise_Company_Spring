package ua.den.controller.command;

import org.springframework.stereotype.Component;
import ua.den.model.exception.AlreadyLoggedInException;
import ua.den.model.exception.InvalidLoginOrPasswordException;
import ua.den.model.entity.dto.User;
import ua.den.model.entity.enums.UserType;
import ua.den.model.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Class {@code LoginCommand} provide methods to process login
 *
 * @author Dean4iq
 * @version 1.0
 */
@Component
public class LoginCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Autowired
    private LoginService loginService;

    /**
     * Calls methods to operate the login process and returns link to the login page
     * or redirects to the user pages
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the login or user page (after successful login)
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");

        User user = new User.Builder()
                .login(request.getParameter("loginUser"))
                .password(request.getParameter("passwordUser"))
                .build();

        if (request.getParameter("login") != null && user.getLogin() != null && !user.getLogin().equals("")) {
            try {
                user = loginService.checkUserData(user);

                if (!userInSystem(user, request)) {
                    return processUserInSystem(user, request);
                }

                throw new AlreadyLoggedInException();
            } catch (InvalidLoginOrPasswordException e) {
                request.setAttribute("invalidLoginOrPassword", true);
                LOG.warn("User with login {} tried to log in the system", user.getLogin());
            } catch (AlreadyLoggedInException e) {
                request.setAttribute("alreadyLoggedIn", true);
                LOG.warn("User with login {} already logged in the system", user.getLogin());
            }
        }

        return "login";
    }

    /**
     * Checks if user with this login already exists in the system
     *
     * @param user    user to check
     * @param request stores and provides user data to process and link to session and context
     * @return true if user is already logged in
     */
    private boolean userInSystem(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        return userList.stream().anyMatch(username -> user.getLogin().equals(username));
    }

    /**
     * Initializes user session and add info to it
     *
     * @param user    user to initialize
     * @param request stores and provides user data to process and link to session and context
     * @return redirect link
     */
    private String processUserInSystem(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        userList.add(user.getLogin());
        request.getSession().setAttribute("User", user);

        LOG.info("User {} successfully logged in the system", user.getLogin());

        if (user.isAdmin()) {
            request.getSession().setAttribute("Role", UserType.ADMIN);
            return "admin";
        } else {
            request.getSession().setAttribute("Role", UserType.USER);
            return "user";
        }
    }
}
