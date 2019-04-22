package ua.den.controller.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ua.den.controller.filter.*;
import ua.den.controller.listener.ContextListener;
import ua.den.controller.listener.SessionListener;

import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

@Configuration
public class Configs {
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        //slr.setDefaultLocale(new Locale("uk_UA"));
        slr.setDefaultLocale(new Locale("uk"));
        return slr;
    }

    /*@Bean
    public FilterRegistrationBean<EncodingFilter> encodingFilter() {
        FilterRegistrationBean<EncodingFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new EncodingFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }*/

    /*@Bean
    public FilterRegistrationBean<LanguageFilter> languageFilter() {
        FilterRegistrationBean<LanguageFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new LanguageFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }*/

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> contextListener() {
        return new ServletListenerRegistrationBean<>(new ContextListener());
    }
}
