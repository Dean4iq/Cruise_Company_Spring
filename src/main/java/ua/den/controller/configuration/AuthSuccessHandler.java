package ua.den.controller.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ua.den.model.entity.enums.UserType;
import ua.den.model.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@Component
@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository repository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession(false);

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.stream().anyMatch(role -> role.equals("ROLE_ADMIN"))) {
            session.setAttribute("Role", UserType.ADMIN);
            session.setAttribute("User", repository.getUserByLogin(authentication.getName()));

            response.sendRedirect(request.getContextPath() + "/admin");
        } else if (roles.stream().anyMatch(role -> role.equals("ROLE_USER"))) {
            session.setAttribute("Role", UserType.USER);
            session.setAttribute("User", repository.getUserByLogin(authentication.getName()));

            response.sendRedirect(request.getContextPath() + "/user");
        }
    }
}
