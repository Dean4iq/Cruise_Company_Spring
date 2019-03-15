package controller.command;

import exception.AlreadyLoggedInException;
import exception.InvalidLoginOrPasswordException;
import exception.NoSuchIdException;
import model.entity.User;
import model.entity.enums.UserType;
import model.service.LoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class LoginCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        User user = new User.Builder()
                .login(request.getParameter("loginUser"))
                .password(request.getParameter("passwordUser"))
                .build();

        try {
            LoginService loginService = LoginService.INSTANCE;

            user = loginService.checkUserData(user);

            if (!userInSystem(user, request)) {
                return processUserInSystem(user, request);
            }

            throw new AlreadyLoggedInException();
        } catch (NoSuchIdException e) {
            request.setAttribute("noSuchId", true);
        } catch (InvalidLoginOrPasswordException e) {
            request.setAttribute("invalidLoginOrPassword", true);
        } catch (AlreadyLoggedInException e) {
            request.setAttribute("alreadyLoggedIn", true);
        }

        return "/login.jsp";
    }

    private boolean userInSystem(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        return userList.stream().anyMatch(username -> user.getLogin().equals(username));
    }

    private String processUserInSystem(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        userList.add(user.getLogin());
        request.getSession().setAttribute("User", user);

        if (user.isAdmin()) {
            request.getSession().setAttribute("Role", UserType.ADMIN);
            return "redirect: /admin";
        } else {
            request.getSession().setAttribute("Role", UserType.USER);
            return "redirect: /user";
        }
    }
}
