package controller.command;

import model.exception.NoResultException;
import model.entity.dto.Cruise;
import model.entity.dto.Route;
import model.service.TourSearchingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PropertiesSource;
import util.ResourceBundleGetter;

import javax.servlet.http.HttpServletRequest;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class SearchCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(SearchCommand.class);
    private TourSearchingService tourSearchingService = TourSearchingService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getParameter("searchCruise") != null) {
            String countryToVisit = request.getParameter("countryToVisit");

            try {
                List<Cruise> cruiseList = tourSearchingService.searchCruisesFullInfo();

                cruiseList = filterCruiseListByCountry(cruiseList, countryToVisit);

                setCruiseDuration(cruiseList);

                request.setAttribute("searchCommitted", true);
                request.setAttribute("cruiseList", cruiseList);
            } catch (NoResultException e) {
                LOG.warn("No results for request: '{}'", countryToVisit);
                request.setAttribute("noResult", true);
            }
        }

        if (request.getParameter("cruiseId") != null) {
            request.getSession().setAttribute("selectedCruiseId", request.getParameter("cruiseId"));
            return "redirect: /user/tickets";
        }

        setCountryMap(request);
        setHarborMap(request);

        return "/WEB-INF/user/search.jsp";
    }

    private List<Cruise> filterCruiseListByCountry(List<Cruise> cruiseList, String country) {
        return cruiseList.stream().filter(cruise ->
                cruise.getRouteList().stream().anyMatch(route ->
                        route.getHarbor().getCountry().getName().equals(country)))
                .collect(Collectors.toList());
    }

    private void setCruiseDuration(List<Cruise> cruiseList) {
        cruiseList.forEach(cruise -> {
            Route routeFrom = cruise.getRouteList().get(0);
            Route routeTo = cruise.getRouteList().get(cruise.getRouteList().size() - 1);
            cruise.setDaysInRoute((int) ChronoUnit.DAYS.between(routeFrom.getDeparture().toInstant(),
                    routeTo.getArrival().toInstant()));
        });
    }

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
