package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.den.model.entity.tables.Cruise;
import ua.den.model.entity.tables.Route;
import ua.den.model.exception.NoResultException;
import ua.den.model.service.CruiseService;
import ua.den.util.PropertiesSource;
import ua.den.util.ResourceBundleGetter;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Controller
@RequestMapping("/user/search")
public class UserSearchController {
    private static final Logger LOG = LogManager.getLogger(UserSearchController.class);
    private static final String USER_SEARCH_PAGE_JSP = "user/search";
    private static final String TICKETS_PAGE_REDIRECT = "redirect:/user/tickets";

    @Autowired
    private CruiseService cruiseService;

    @GetMapping("")
    public String getSearchPage(HttpServletRequest request) {
        setCountryMap(request);

        return USER_SEARCH_PAGE_JSP;
    }

    @PostMapping("/commit-search")
    public String processSearch(@RequestParam("cruiseId") Integer cruiseId,
                                HttpServletRequest request) {
        request.getSession().setAttribute("selectedCruiseId", request.getParameter("cruiseId"));
        return TICKETS_PAGE_REDIRECT;
    }

    @PostMapping("")
    public String processSearch(@RequestParam("countryToVisit") String country,
                                HttpServletRequest request) {
        try {
            List<Cruise> cruiseList = cruiseService.searchCruiseByCountry(country);

            setCruiseDuration(cruiseList);

            request.setAttribute("searchCommitted", true);
            request.setAttribute("cruiseList", cruiseList);
        } catch (NoResultException e) {
            LOG.warn("No results for request: '{}'", country);
            request.setAttribute("noResult", true);
        }


        setCountryMap(request);
        setHarborMap(request);

        return USER_SEARCH_PAGE_JSP;
    }

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

    private void setCountryMap(HttpServletRequest request) {
        Locale sessionLocale = LocaleContextHolder.getLocale();

        Map<String, String> countryMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.COUNTRY.source, sessionLocale);

        countryMap = countryMap.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        request.setAttribute("countryMap", countryMap);
    }

    private void setHarborMap(HttpServletRequest request) {
        Locale sessionLocale = LocaleContextHolder.getLocale();

        Map<String, String> harborMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.HARBOR.source, sessionLocale);

        harborMap = harborMap.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        request.setAttribute("harborMap", harborMap);
    }
}
