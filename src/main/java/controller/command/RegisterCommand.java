package controller.command;

import exception.NotUniqueLoginException;
import model.entity.User;
import model.entity.enums.UserType;
import model.service.RegisterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    private RegisterService registerService;

    @Override
    public String execute(HttpServletRequest request) {
        User user = getRegisterData(request);
        registerService = RegisterService.INSTANCE;

        try {
            if (registerService.checkUniqueLogin(user.getLogin()) && checkFieldRegEx(user)) {
                registerService.registerNewUser(user);

                return initializeUserSession(user, request);
            }
        } catch (NotUniqueLoginException e) {
            LOG.error(e);
        }
        return "/register.jsp";
    }

    private User getRegisterData(HttpServletRequest request) {
        return new User.Builder()
                .login(request.getParameter("userLogin"))
                .password(request.getParameter("userPassword"))
                .email(request.getParameter("userEmail"))
                .name(request.getParameter("userName"))
                .surname(request.getParameter("userSurname"))
                .build();
    }

    private boolean checkFieldRegEx(User user) {
        //TODO
        return true;
    }

    private String initializeUserSession(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        userList.add(user.getLogin());
        request.getSession().setAttribute("User", user);

        request.getSession().setAttribute("Role", UserType.USER);
        return "redirect: /user";

    }
}
