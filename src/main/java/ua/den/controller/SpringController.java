package ua.den.controller;

import org.springframework.context.ApplicationContext;
import ua.den.controller.command.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class SpringController {
    private static final Map<String, Command> commandMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        System.out.println("aa");
        commandMap.put("admin/users", applicationContext.getBean(AdminSearchCommand.class));
        commandMap.put("user/cart", applicationContext.getBean(CartCommand.class));
    }


}