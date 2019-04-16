package ua.den.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.den.controller.command.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SpringController implements ApplicationContextAware {
    private static final Map<String, Command> commandMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        commandMap.put("error", new ExceptionCommand());
        commandMap.put("login", applicationContext.getBean(LoginCommand.class));
        commandMap.put("user", new UserCommand());
        commandMap.put("admin", new AdminCommand());
        commandMap.put("logout", new LogoutCommand());
        commandMap.put("register", new RegisterCommand());
        commandMap.put("user/search", new SearchCommand());
        commandMap.put("admin/search/users", new AdminSearchCommand());
        commandMap.put("user/tickets", new CabinSelectionCommand());
        commandMap.put("user/cart", new CartCommand());
    }

    @RequestMapping(value = {"/{path}"})
    public String processRequest(@PathVariable("path") String path, HttpServletRequest request,
                               HttpServletResponse response)
            throws ServletException, IOException {
        String action = commandMap.getOrDefault(path, (req) -> "redirect: /login").execute(request);

        /*if (action.contains("redirect: ")) {
            response.sendRedirect(request.getContextPath() + action.replaceAll("redirect: ", ""));
        } else {
            request.getRequestDispatcher(action).forward(request, response);
        }*/

        return action;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}