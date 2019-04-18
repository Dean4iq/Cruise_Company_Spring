package ua.den.model.service;

import org.springframework.web.context.annotation.SessionScope;
import ua.den.model.exception.NoResultException;
import ua.den.model.entity.dto.Cruise;
import ua.den.model.repository.CruiseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class {@code TourSearchingService} provides methods to receive data from DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
@Service
@SessionScope
public class TourSearchingService {
    private static final Logger LOG = LogManager.getLogger(TourSearchingService.class);

    @Autowired
    private CruiseRepository cruiseRepository;

    /**
     * Receives and provides list of cruises from DB
     *
     * @return list of cruises
     * @throws NoResultException if there will be no cruise in DB
     */
    public List<Cruise> searchCruiseByCountry(String country) throws NoResultException {
        LOG.trace("searchCruisesFullInfo()");

        List<Cruise> cruiseList = cruiseRepository.getCruiseByCountry(country);

        if (cruiseList.isEmpty()) {
            throw new NoResultException();
        }

        return cruiseList;
    }
}
