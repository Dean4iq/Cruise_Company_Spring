package controller.command;

import exception.NotUniqueLoginException;
import model.entity.User;
import model.entity.enums.UserType;
import model.service.RegisterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RegExSources;
import util.RegExStringsGetter;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    private RegisterService registerService;

    @Override
    public String execute(HttpServletRequest request) {
        User user = getRegisterData(request);
        registerService = RegisterService.INSTANCE;

        try {
            if (registerService.checkUniqueLogin(user.getLogin()) && validateFields(user, request)) {
                registerService.registerNewUser(user);

                return initializeUserSession(user, request);
            }
        } catch (NotUniqueLoginException e) {
            request.setAttribute("notUniqueLogin", true);
            LOG.warn("Login {} already taken", e);
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

    private boolean validateFields(User user, HttpServletRequest request) {
        Map<String, String> userData = convertUserFieldsToMap(user);

        Map<String, String> resultedMap = userData.entrySet().stream().filter(elem -> {
            String regexKey = Arrays.stream(RegExSources.values()).filter(source ->
                    elem.getKey().equals(source.getField())).findFirst().get().getLink();
            if (!checkFieldRegEx(elem.getValue(), regexKey)) {
                request.setAttribute(elem.getKey() + "Invalid", true);
                return true;
            }
            return false;
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return resultedMap.isEmpty();
    }

    private boolean checkFieldRegEx(String field, String regexKey) {
        String regexString = new RegExStringsGetter().getRegExString(regexKey);
        return (field.matches(regexString));
    }

    private Map<String, String> convertUserFieldsToMap(User user) {
        Map<String, String> userData = new HashMap<>();

        userData.put("login", user.getLogin());
        userData.put("password", user.getPassword());
        userData.put("email", user.getEmail());
        userData.put("name", user.getName());
        userData.put("surname", user.getSurname());

        return userData;
    }

    private String initializeUserSession(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        userList.add(user.getLogin());
        request.getSession().setAttribute("User", user);

        request.getSession().setAttribute("Role", UserType.USER);
        return "redirect: /user";

    }
}
