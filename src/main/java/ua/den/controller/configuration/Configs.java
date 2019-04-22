package ua.den.controller.configuration;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.den.controller.listener.ContextListener;
import ua.den.controller.listener.SessionListener;

import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class Configs {
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> contextListener() {
        return new ServletListenerRegistrationBean<>(new ContextListener());
    }
}
