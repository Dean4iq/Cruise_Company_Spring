package model.service;

import model.exception.NoSuchIdException;
import model.dao.*;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code CartService} provides methods to receive and create data from/to DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
public class CartService {
    private static final Logger LOG = LogManager.getLogger(CartService.class);
    private DaoFactory daoFactory;
    private CruiseDao cruiseDao;
    private RoomDao roomDao;
    private ExcursionDao excursionDao;
    private TicketDao ticketDao;
    private TicketExcursionDao ticketExcursionDao;

    private CartService() {
        daoFactory = JDBCDaoFactory.getInstance();
    }

    private static class SingletonHolder {
        private static final CartService instance = new CartService();
    }

    public static CartService getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Receives and returns cruise info from DB
     *
     * @param cruiseId id of cruise to find
     * @return cruise from DB
     */
    public Cruise getCruiseInfo(String cruiseId) {
        LOG.trace("getCruiseInfo({})", cruiseId);

        cruiseDao = daoFactory.createCruiseDao();

        List<Cruise> cruiseList = cruiseDao.findFullCruiseInfo();

        try {
            cruiseDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return cruiseList.stream().filter(cruise ->
                cruise.getId() == Integer.parseInt(cruiseId)).findFirst().orElse(null);
    }

    /**
     * Receives and returns room info from DB
     *
     * @param roomId id of room to find
     * @return room from DB
     */
    public Room getRoomInfo(String roomId) {
        LOG.trace("getRoomInfo({})", roomId);

        roomDao = daoFactory.createRoomDao();
        Room room = roomDao.getFullInfo(Integer.parseInt(roomId));

        try {
            roomDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return room;
    }

    /**
     * Receives and returns excursion info from DB
     *
     * @param excursionId id of excursion to find
     * @return excursion from DB
     * @throws NoSuchIdException if excursion will not be found
     */
    public Excursion getExcursionById(int excursionId) throws NoSuchIdException {
        LOG.trace("getExcursionById({})", excursionId);

        excursionDao = daoFactory.createExcursionDao();
        Excursion excursion = excursionDao.findById(excursionId);

        try {
            excursionDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return excursion;
    }

    /**
     * Receives and returns list of excursion from DB for specified cruise
     *
     * @param cruiseId id of cruise to find available excursions
     * @return list of excursions from DB for cruise
     */
    public List<Excursion> getExcursionList(String cruiseId) {
        LOG.trace("getExcursionList({})", cruiseId);

        excursionDao = daoFactory.createExcursionDao();
        List<Excursion> excursionList = excursionDao.getExcursionsForCruise(Integer.parseInt(cruiseId));

        try {
            excursionDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return excursionList;
    }

    /**
     * Adds ticket and selected excursions from cart info to DB
     *
     * @param ticket to be added
     * @param excursions list of excursions to be added to DB
     * @throws NoSuchIdException if ticket will be not found
     */
    public void applyTicketPurchasing(Ticket ticket, List<Excursion> excursions) throws NoSuchIdException {
        LOG.trace("applyTicketPurchasing()");
        int ticketId = addTicketInDB(ticket);

        if (excursions != null && !excursions.isEmpty()) {
            addExcursionsInDB(excursions, ticketId);
        }
    }

    /**
     * Adds ticket in DB
     *
     * @param ticket to be added
     * @return id of row in DB after 'Create' ticket
     * @throws NoSuchIdException if created ticket row will be not found
     */
    private int addTicketInDB(Ticket ticket) throws NoSuchIdException {
        LOG.trace("addTicketInDB()");

        ticketDao = daoFactory.createTicketDao();
        int id = ticketDao.createAndReturnId(ticket);

        try {
            ticketDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        if (id == -1) {
            throw new NoSuchIdException(Integer.toString(id));
        }

        return id;
    }

    /**
     * Adds excursion in DB
     *
     * @param excursions from cart to be added
     * @param ticketId ticket id to join with Ticket table in DB
     */
    private void addExcursionsInDB(List<Excursion> excursions, int ticketId) {
        LOG.trace("addExcursionsInDB()");
        List<TicketExcursion> ticketExcursionList = new ArrayList<>();

        excursions.forEach(excursion -> {
            TicketExcursion ticketExcursion = new TicketExcursion();

            ticketExcursion.setTicketId(ticketId);
            ticketExcursion.setExcursionId(excursion.getId());

            ticketExcursionList.add(ticketExcursion);
        });

        ticketExcursionDao = daoFactory.createTicketExcursionDao();
        ticketExcursionDao.addList(ticketExcursionList);

        try {
            ticketExcursionDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
