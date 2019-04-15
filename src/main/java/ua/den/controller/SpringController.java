package ua.den.controller;

import ua.den.controller.command.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SpringController {
    private static final Map<String, Command> commandMap = new HashMap<>();

    @PostConstruct
    public void init() {
        commandMap.put("exception", new ExceptionCommand());
        commandMap.put("login", new LoginCommand());
        commandMap.put("user", new UserCommand());
        commandMap.put("admin", new AdminCommand());
        commandMap.put("logout", new LogoutCommand());
        commandMap.put("register", new RegisterCommand());
        commandMap.put("user/search", new SearchCommand());
        commandMap.put("admin/search/users", new AdminSearchCommand());
        commandMap.put("user/tickets", new CabinSelectionCommand());
        commandMap.put("user/cart", new CartCommand());
    }

    @GetMapping("/{link}")
    public void processRequest(@PathVariable("link") String link, HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException {
        String action = commandMap.getOrDefault(link, new LoginCommand()).execute(request);

        if (action.contains("redirect: ")) {
            response.sendRedirect(request.getContextPath() + action.replaceAll("redirect: ", ""));
        } else {
            request.getRequestDispatcher(action).forward(request, response);
        }
    }
}