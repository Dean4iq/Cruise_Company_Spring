package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.den.model.entity.tables.User;
import ua.den.model.exception.NotUniqueLoginException;
import ua.den.model.repository.AuthorityRepository;
import ua.den.model.service.UserService;
import ua.den.util.RegExSources;
import ua.den.util.RegExStringsGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class GuestController {
    private static final Logger LOG = LogManager.getLogger(GuestController.class);
    private static final String LOGIN_PAGE_JSP = "login";
    private static final String REGISTER_PAGE_JSP = "register";
    private static final String ADMIN_PAGE_REDIRECT = "redirect:/admin";
    private static final String USER_PAGE_REDIRECT = "redirect:/user";

    private UserService userService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    public String setStartPage() {
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());

        if (roles.stream().anyMatch(role -> role.equals("ROLE_ADMIN"))) {
            return ADMIN_PAGE_REDIRECT;
        } else if (roles.stream().anyMatch(role -> role.equals("ROLE_USER"))) {
            return USER_PAGE_REDIRECT;
        }

        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String setloginPage() {
        return LOGIN_PAGE_JSP;
    }

    @GetMapping("/register")
    public String accessRegisterPage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (Optional.ofNullable((User) session.getAttribute("User")).isPresent()) {
            User user = (User) session.getAttribute("User");
            String role = user.getAuthority().getRole().name();

            if (role.equals("ROLE_USER")) {
                return USER_PAGE_REDIRECT;
            } else if (role.equals("ROLE_ADMIN")) {
                return ADMIN_PAGE_REDIRECT;
            }
        }

        return REGISTER_PAGE_JSP;
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @Param("login") final String login,
                                      @Param("password") final String password,
                                      @Valid @Param("email") final String email,
                                      @Valid @Param("name") final String name,
                                      @Valid @Param("surname") final String surname,
                                      HttpServletRequest request) {
        User user = new User.Builder()
                .login(login)
                .password(password)
                .email(email)
                .name(name)
                .surname(surname)
                .build();

        try {
            if (validateFields(user, request) && userService.checkUniqueLogin(login)) {
                userService.create(user);

                LOG.info("User {} registered", user.getLogin());
                return "redirect:/login";
            }
        } catch (NotUniqueLoginException e) {
            request.setAttribute("notUniqueLogin", true);
        }

        return REGISTER_PAGE_JSP;
    }

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

    private Map<String, String> convertUserFieldsToMap(User user) {
        Map<String, String> userData = new HashMap<>();

        userData.put("login", user.getLogin());
        userData.put("password", user.getPassword());
        userData.put("email", user.getEmail());
        userData.put("name", user.getName());
        userData.put("surname", user.getSurname());

        return userData;
    }

    private boolean checkFieldRegEx(String field, String regexKey) {
        String regexString = new RegExStringsGetter().getRegExString(regexKey);
        return (field.matches(regexString));
    }
}
