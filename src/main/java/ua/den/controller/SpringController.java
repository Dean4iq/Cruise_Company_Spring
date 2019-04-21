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

public class SpringController {
    private static final Map<String, Command> commandMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        System.out.println("aa");
        commandMap.put("error", applicationContext.getBean(ExceptionCommand.class));
        commandMap.put("login", applicationContext.getBean(LoginCommand.class));
        commandMap.put("user", applicationContext.getBean(UserCommand.class));
        commandMap.put("admin", applicationContext.getBean(AdminCommand.class));
        commandMap.put("logout", applicationContext.getBean(LogoutCommand.class));
        commandMap.put("register", applicationContext.getBean(RegisterCommand.class));
        commandMap.put("user/search", applicationContext.getBean(SearchCommand.class));
        commandMap.put("admin/users", applicationContext.getBean(AdminSearchCommand.class));
        commandMap.put("user/tickets", applicationContext.getBean(CabinSelectionCommand.class));
        commandMap.put("user/cart", applicationContext.getBean(CartCommand.class));
    }


}