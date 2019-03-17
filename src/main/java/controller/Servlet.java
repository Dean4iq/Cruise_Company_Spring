package controller;

import controller.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Class {@code Servlet} exists to provide control of user requests
 *
 * @author Dean4iq
 * @version 1.0
 * @see HttpServlet
 */
public class Servlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Servlet.class);

    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        servletConfig.getServletContext().setAttribute("loggedUsers", new HashSet<String>());

        commands.put("exception", new ExceptionCommand());
        commands.put("login", new LoginCommand());
        commands.put("user", new UserCommand());
        commands.put("admin", new AdminCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("search", new SearchCommand());
        commands.put("admin/search/users",new AdminSearchCommand());

        LOG.trace("Servlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Defines which class should process the user's request
     * and redirects or forwards to the page
     *
     * @param request  request to process
     * @param response response to return
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/company/", "");

        Command command = commands.getOrDefault(path,
                (r) -> "redirect: /login");
        String page = command.execute(request);

        if (page.contains("redirect: ")) {
            LOG.trace("Servlet redirect to " + page);
            response.sendRedirect(request.getContextPath() + page.replaceAll("redirect: ", ""));
        } else {
            LOG.trace("Servlet forward to " + page);
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
