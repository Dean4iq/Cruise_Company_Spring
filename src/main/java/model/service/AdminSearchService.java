package model.service;

import model.exception.NoSuchIdException;
import model.dao.DaoFactory;
import model.dao.TicketDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
