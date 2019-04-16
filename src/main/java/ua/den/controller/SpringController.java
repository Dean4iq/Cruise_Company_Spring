package ua.den.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.den.controller.command.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SpringController implements ApplicationContextAware {
    private static final Map<String, Command> commandMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        commandMap.put("error", applicationContext.getBean(ExceptionCommand.class));
        commandMap.put("login", applicationContext.getBean(LoginCommand.class));
        commandMap.put("user", applicationContext.getBean(UserCommand.class));
        commandMap.put("admin", applicationContext.getBean(AdminCommand.class));
        commandMap.put("logout", applicationContext.getBean(LogoutCommand.class));
        commandMap.put("register", applicationContext.getBean(RegisterCommand.class));
        commandMap.put("user/search", applicationContext.getBean(SearchCommand.class));
        commandMap.put("admin/search/users", applicationContext.getBean(AdminSearchCommand.class));
        commandMap.put("user/tickets", applicationContext.getBean(CabinSelectionCommand.class));
        commandMap.put("user/cart", applicationContext.getBean(CartCommand.class));
    }

    @RequestMapping("/")
    public String processEmptyRequest() {
        return "redirect:/login";
    }

    @RequestMapping(value = {"/{path}"})
    public String processRequest(@PathVariable("path") String path, HttpServletRequest request) {
        System.out.println("1" + path);
        String action = commandMap.getOrDefault(path, req -> "redirect:/login").execute(request);
        System.out.println("2" + action);
        return action;
    }

    @RequestMapping(value = {"/{path1}/{path2}"})
    public String processRequest(@PathVariable("path1") String path1, @PathVariable("path2") String path2,
                                 HttpServletRequest request) {
        System.out.println("1" + path1 + "/" + path2);
        String action = commandMap.getOrDefault(path1 + "/" + path2, req -> "redirect:/login").execute(request);
        System.out.println("2" + action);
        return action;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}