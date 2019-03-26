package model.service;

import model.exception.NoSuchIdException;
import model.dao.CruiseDao;
import model.dao.DaoFactory;
import model.dao.RoomDao;
import model.dao.TicketDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.Cruise;
import model.entity.dto.Room;
import model.entity.dto.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Class {@code CabinSelectionService} provides methods to receive data from DB for servlet commands
 *
 * @author Dean4iq
 * @version 1.0
 */
public class CabinSelectionService {
    private static final Logger LOG = LogManager.getLogger(CabinSelectionService.class);
    private DaoFactory daoFactory;
    private RoomDao roomDao;
    private CruiseDao cruiseDao;
    private TicketDao ticketDao;

    private CabinSelectionService() {
        daoFactory = JDBCDaoFactory.getInstance();
    }

    private static class SingletonHolder {
        private static final CabinSelectionService instance = new CabinSelectionService();
    }

    public static CabinSelectionService getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Receives and returns list of rooms on ship assigned for cruise
     *
     * @param cruiseId id of cruise to find ship with rooms
     * @return list of rooms on ship
     */
    public List<Room> getCruiseLoadInfo(String cruiseId) {
        LOG.trace("getCruiseInfo({})", cruiseId);

        roomDao = daoFactory.createRoomDao();

        List<Room> roomList = roomDao.findByCruise(Integer.parseInt(cruiseId));

        try {
            roomDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return roomList;
    }

    /**
     * Receives a cruise from DB and returns it to command
     *
     * @param cruiseId id of the cruise
     * @return cruise with filled fields
     * @throws NoSuchIdException if cruise will be not found
     */
    public Cruise getSearchedCruiseInfo(String cruiseId) throws NoSuchIdException {
        LOG.trace("getSearchedCruiseInfo({})", cruiseId);

        cruiseDao = daoFactory.createCruiseDao();

        try {
            return cruiseDao.findById(Integer.parseInt(cruiseId));
        } finally {
            try {
                cruiseDao.close();
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }

    /**
     * Receives a list of tickets from DB for cruise and returns it to the command
     *
     * @param cruiseId id of cruise to find tickets for it
     * @return list of tickets for cruise
     */
    public List<Ticket> getTicketsForCruise(String cruiseId) {
        LOG.trace("getTicketsForCruise({})", cruiseId);

        ticketDao = daoFactory.createTicketDao();
        List<Ticket> tickets = ticketDao.getTicketsForCruise(Integer.parseInt(cruiseId));

        try {
            ticketDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return tickets;
    }
}
