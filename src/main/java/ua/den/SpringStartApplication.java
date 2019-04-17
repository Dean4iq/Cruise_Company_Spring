package ua.den;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import ua.den.controller.filter.*;
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
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }

    @Bean
    public FilterRegistrationBean<EncodingFilter> encodingFilter(){
        FilterRegistrationBean<EncodingFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new EncodingFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<LanguageFilter> languageFilter(){
        FilterRegistrationBean<LanguageFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LanguageFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter(){
        FilterRegistrationBean<AdminFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/admin/*");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<GuestFilter> guestFilter(){
        FilterRegistrationBean<GuestFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new GuestFilter());
        registrationBean.addUrlPatterns("/login");
        registrationBean.addUrlPatterns("/register");

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<UserFilter> userFilter(){
        FilterRegistrationBean<UserFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns("/user/*");

        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> contextListener() {
        return new ServletListenerRegistrationBean<>(new ContextListener());
    }
}
