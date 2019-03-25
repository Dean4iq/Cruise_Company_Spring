package model.service;

import model.exception.NoResultException;
import model.dao.CruiseDao;
import model.dao.DaoFactory;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.Cruise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class {@code TourSearchingService} provides methods to receive data from DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
public class TourSearchingService {
    private static final Logger LOG = LogManager.getLogger(TourSearchingService.class);
    private DaoFactory daoFactory;
    private CruiseDao cruiseDao;

    private TourSearchingService() {
        daoFactory = JDBCDaoFactory.getInstance();
    }

    private static class SingletonHolder {
        private static final TourSearchingService instance = new TourSearchingService();
    }

    public static TourSearchingService getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Receives and provides list of cruises from DB
     *
     * @return list of cruises
     * @throws NoResultException if there will be no cruise in DB
     */
    public List<Cruise> searchCruisesFullInfo() throws NoResultException {
        LOG.trace("searchCruisesFullInfo()");

        cruiseDao = daoFactory.createCruiseDao();

        List<Cruise> cruiseList = cruiseDao.findFullCruiseInfo();
        try {
            cruiseDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        if (cruiseList.isEmpty()) {
            throw new NoResultException();
        }

        return cruiseList;
    }
}
