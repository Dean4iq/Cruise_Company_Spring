package controller.command;

import model.exception.NotUniqueLoginException;
import model.entity.dto.User;
import model.entity.enums.UserType;
import model.service.RegisterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.RegExSources;
import util.RegExStringsGetter;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class {@code RegisterCommand} provide methods to process registration
 *
 * @author Dean4iq
 * @version 1.0
 */
public class RegisterCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);
    private RegisterService registerService = RegisterService.getInstance();

    /**
     * Calls methods to operate the registration process and returns link to the register page
     * or redirects to the user page (after registration)
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the login or user page (after successful registration)
     */
    @Override
    public String execute(HttpServletRequest request) {
        LOG.trace("Execute()");

        User user = getRegisterData(request);

        try {
            if (validateFields(user, request) && registerService.checkUniqueLogin(user.getLogin())) {
                System.out.println("WAZZZUP");
                registerService.registerNewUser(user);

                return initializeUserSession(user, request);
            }
        } catch (NotUniqueLoginException e) {
            request.setAttribute("notUniqueLogin", true);
            LOG.warn("Login {} already taken", e);
        }
        return "/register.jsp";
    }

    /**
     * Receives the register data and initializes a new user
     *
     * @param request stores and provides user data to process and link to session and context
     * @return new user with filled fields
     */
    private User getRegisterData(HttpServletRequest request) {
        return new User.Builder()
                .login(request.getParameter("userLogin"))
                .password(request.getParameter("userPassword"))
                .email(request.getParameter("userEmail"))
                .name(request.getParameter("userName"))
                .surname(request.getParameter("userSurname"))
                .build();
    }

    /**
     * Method to validate fields on regular expressions
     *
     * @param user provides fields to validate
     * @param request stores and provides user data to process and link to session and context
     * @return true if fields are valid
     */
    private boolean validateFields(User user, HttpServletRequest request) {
        Map<String, String> userData = convertUserFieldsToMap(user);

        if (userData.entrySet().stream().anyMatch(elem ->
                elem.getValue() == null || elem.getValue().isEmpty())) {
            return false;
        }

        Map<String, String> resultedMap = userData.entrySet().stream().filter(elem -> {
            String regexKey = Arrays.stream(RegExSources.values()).filter(source ->
                    source.getField().equals(elem.getKey())
            ).findAny().orElse(null).getLink();

            if (!checkFieldRegEx(elem.getValue(), regexKey)) {
                request.setAttribute(elem.getKey() + "Invalid", true);
                return true;
            }
            return false;
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return resultedMap.isEmpty();
    }

    /**
     * Method to validate concrete user field
     *
     * @param field to check
     * @param regexKey key to regular expression string
     * @return true if field is valid
     */
    private boolean checkFieldRegEx(String field, String regexKey) {
        String regexString = new RegExStringsGetter().getRegExString(regexKey);
        return (field.matches(regexString));
    }

    /**
     * Initializes new map with user data
     *
     * @param user provides user data
     * @return new map with user data
     */
    private Map<String, String> convertUserFieldsToMap(User user) {
        Map<String, String> userData = new HashMap<>();

        userData.put("login", user.getLogin());
        userData.put("password", user.getPassword());
        userData.put("email", user.getEmail());
        userData.put("name", user.getName());
        userData.put("surname", user.getSurname());

        return userData;
    }

    /**
     * Initializes new user session after successful registration
     *
     * @param user date to initialize new session
     * @param request stores and provides user data to process and link to session and context
     * @return link to user homepage
     */
    private String initializeUserSession(User user, HttpServletRequest request) {
        Set<String> userList = (HashSet<String>) request.getServletContext().getAttribute("userList");

        userList.add(user.getLogin());
        request.getSession().setAttribute("User", user);

        request.getSession().setAttribute("Role", UserType.USER);
        return "redirect: /user";
    }
}
