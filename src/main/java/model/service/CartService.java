package model.service;

import exception.NoSuchIdException;
import model.dao.*;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public enum CartService {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(CartService.class);
    private DaoFactory daoFactory;
    private CruiseDao cruiseDao;
    private RoomDao roomDao;
    private ExcursionDao excursionDao;
    private TicketDao ticketDao;
    private TicketExcursionDao ticketExcursionDao;

    CartService() {
        daoFactory = JDBCDaoFactory.getInstance();
    }

    public Cruise getCruiseInfo(String cruiseId) {
        cruiseDao = daoFactory.createCruiseDao();

        List<Cruise> cruiseList = cruiseDao.findFullCruiseInfo();

        try {
            cruiseDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return cruiseList.stream().filter(cruise ->
                cruise.getId() == Integer.parseInt(cruiseId)).findFirst().get();
    }

    public Room getRoomInfo(String roomId) {
        roomDao = daoFactory.createRoomDao();
        Room room = roomDao.getFullInfo(Integer.parseInt(roomId));

        try {
            roomDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return room;
    }

    public List<Excursion> getExcursionList(String cruiseId) {
        excursionDao = daoFactory.createExcursionDao();
        List<Excursion> excursionList = excursionDao.getExcursionsForCruise(Integer.parseInt(cruiseId));

        try {
            excursionDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return excursionList;
    }

    public void applyTicketPurchasing(Ticket ticket, List<Excursion> excursions) throws NoSuchIdException {
        int ticketId = addTicketInDB(ticket);
        addExcursionsInDB(excursions, ticketId);
    }

    private int addTicketInDB(Ticket ticket) throws NoSuchIdException {
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

    private void addExcursionsInDB(List<Excursion> excursions, int ticketId) {
        List<TicketExcursion> ticketExcursionList = new ArrayList<>();

        excursions.forEach(excursion->{
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
