package model.service;

import exception.NoResultException;
import model.dao.CruiseDao;
import model.dao.DaoFactory;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.Cruise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public enum TourSearchingService {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(TourSearchingService.class);
    private DaoFactory daoFactory;
    private CruiseDao cruiseDao;

    TourSearchingService() {
        daoFactory = JDBCDaoFactory.getInstance();
        cruiseDao = daoFactory.createCruiseDao();
    }

    public List<Cruise> searchCruisesFullInfo() throws NoResultException {
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
