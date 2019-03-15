package controller.filter;

import model.entity.enums.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GuestFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(GuestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.trace("GuestFilter class init()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession(true);

        if (session.getAttribute("Role").equals(UserType.GUEST)) {
            LOG.trace("Access granted");
            filterChain.doFilter(httpRequest, httpResponse);
        } else if (session.getAttribute("Role").equals(UserType.USER)){
            LOG.trace("Access denied");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user");
        } else if (session.getAttribute("Role").equals(UserType.ADMIN)){
            LOG.trace("Access denied");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin");
        }
    }

    @Override
    public void destroy() {
        LOG.trace("GuestFilter class destroy()");
    }
}