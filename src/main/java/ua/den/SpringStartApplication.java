package ua.den;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import ua.den.controller.listener.ContextListener;
import ua.den.controller.listener.SessionListener;

import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class SpringStartApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringStartApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringStartApplication.class, args);
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<HttpSessionListener>(new SessionListener());
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> contextListener() {
        return new ServletListenerRegistrationBean<ServletContextListener>(new ContextListener());
    }
}
