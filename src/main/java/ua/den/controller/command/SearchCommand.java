package ua.den.controller.command;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ua.den.model.exception.NoResultException;
import ua.den.model.entity.tables.Cruise;
import ua.den.model.entity.tables.Route;
import ua.den.model.service.TourSearchingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ua.den.util.PropertiesSource;
import ua.den.util.ResourceBundleGetter;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

/**
 * Class {@code SearchCommand} provide methods to search cruises
 *
 * @author Dean4iq
 * @version 1.0
 */
@Component
@SessionScope
public class SearchCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(SearchCommand.class);
    private static final String USER_SEARCH_PAGE_JSP = "user/search";
    private static final String TICKETS_PAGE_REDIRECT = "redirect:/user/tickets";

    @Autowired
    private TourSearchingService tourSearchingService;

    /**
     * Calls methods to get cruise info and provides them to the user and returns link to
     * the search page
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the cruise search page or to the room selecting page (after cruise selection)
     */
    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("searchCruise") != null) {
            String countryToVisit = request.getParameter("countryToVisit");

            try {
                List<Cruise> cruiseList = tourSearchingService.searchCruiseByCountry(countryToVisit);

                setCruiseDuration(cruiseList);

                request.setAttribute("searchCommitted", true);
                request.setAttribute("cruiseList", cruiseList);
            } catch (NoResultException e) {
                LOG.warn("No results for request: '{}'", countryToVisit);
                request.setAttribute("noResult", true);
            }
        } else if (request.getParameter("cruiseId") != null) {
            request.getSession().setAttribute("selectedCruiseId", request.getParameter("cruiseId"));
            return TICKETS_PAGE_REDIRECT;
        }

        setCountryMap(request);
        setHarborMap(request);

        return USER_SEARCH_PAGE_JSP;
    }

    /**
     * Initializes duration field of cruise
     *
     * @param cruiseList list of cruises
     */
    private void setCruiseDuration(List<Cruise> cruiseList) {
        Comparator<Route> routeComparator = Comparator.comparingLong(o -> o.getArrival().getTime());
        cruiseList.forEach(cruise ->
                cruise.getRouteList().sort(routeComparator)
        );

        cruiseList.forEach(cruise -> {
            Route routeFrom = cruise.getRouteList().get(0);
            Route routeTo = cruise.getRouteList().get(cruise.getRouteList().size() - 1);
            cruise.setDaysInRoute((int) ChronoUnit.DAYS.between(routeFrom.getDeparture().toInstant(),
                    routeTo.getArrival().toInstant()));
        });
    }

    /**
     * Method to add locale values of country names
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void setCountryMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute("sessionLanguage");

        Map<String, String> countryMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.COUNTRY.source, sessionLanguage);

        countryMap = countryMap.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        request.setAttribute("countryMap", countryMap);
    }

    /**
     * Method to add locale values of harbor names
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void setHarborMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute("sessionLanguage");

        Map<String, String> harborMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.HARBOR.source, sessionLanguage);

        harborMap = harborMap.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        request.setAttribute("harborMap", harborMap);
    }
}
