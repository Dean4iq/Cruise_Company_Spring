package ua.den.controller.command;

import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;

@SessionScope
public interface Command {
    String execute(HttpServletRequest request);
}
