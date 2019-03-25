package model.service;

import model.exception.NoSuchIdException;
import model.dao.DaoFactory;
import model.dao.TicketDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class {@code AdminSearchService} provides methods to receive data from DB for servlet commands
 *
 * @author Dean4iq
 * @version 1.0
 */
public class AdminSearchService {
    private static final Logger LOG = LogManager.getLogger(AdminSearchService.class);
    private DaoFactory daoFactory;
    private TicketDao ticketDao;

    private AdminSearchService() {
        daoFactory = JDBCDaoFactory.getInstance();
    }

    private static class SingletonHolder {
        private static final AdminSearchService instance = new AdminSearchService();
    }

    public static AdminSearchService getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Receives and returns ticket data
     *
     * @param ticketId id of searched ticket
     * @return ticket with filled data from DB
     * @throws NoSuchIdException if ticket not found in DB
     */
    public Ticket getTicketInfo(int ticketId) throws NoSuchIdException {
        LOG.trace("getTicketInfo({})", ticketId);

        ticketDao = daoFactory.createTicketDao();
        Ticket ticket = ticketDao.findFullTicketInfo(ticketId);

        try {
            ticketDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        return ticket;
    }
}
