package ua.den.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.den.util.LanguageISO;
import ua.den.util.PropertiesSource;
import ua.den.util.ResourceBundleGetter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger(LanguageFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.trace("LanguageFilter init()");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        String selectedLanguage = request.getParameter("preferredLanguage");
        String sessionLanguage = (String) session.getAttribute("sessionLanguage");

        if (selectedLanguage != null && sessionLanguage != null && !sessionLanguage.equals(selectedLanguage)) {
            session.setAttribute("sessionLanguage", selectedLanguage);

            session.setAttribute("sessionLocalization",
                    ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.LOCALIZATION_STRINGS.source,
                            selectedLanguage));
        } else if (sessionLanguage == null) {
            session.setAttribute("sessionLanguage", LanguageISO.UKRAINIAN.getCode());

            session.setAttribute("sessionLocalization",
                    ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.LOCALIZATION_STRINGS.source,
                            LanguageISO.UKRAINIAN.getCode()));
        }

        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
        LOG.trace("LanguageFilter destroy()");
    }
}
