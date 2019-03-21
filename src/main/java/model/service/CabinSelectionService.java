package model.service;

import exception.NoSuchIdException;
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

public enum CabinSelectionService {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(CabinSelectionService.class);
    private DaoFactory daoFactory;
    private RoomDao roomDao;
    private CruiseDao cruiseDao;
    private TicketDao ticketDao;

    CabinSelectionService() {
        daoFactory = JDBCDaoFactory.getInstance();
    }

    public List<Room> getCruiseLoadInfo(String cruiseId) {
        roomDao = daoFactory.createRoomDao();

        List<Room> roomList = roomDao.findByCruise(Integer.parseInt(cruiseId));

        try {
            roomDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return roomList;
    }

    public Cruise getSearchedCruiseInfo(String cruiseId) throws NoSuchIdException {
        cruiseDao = daoFactory.createCruiseDao();

        Cruise cruise = cruiseDao.findById(Integer.parseInt(cruiseId));

        try {
            cruiseDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return cruise;
    }

    public List<Ticket> getTicketsForCruise(String cruiseId) {
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
