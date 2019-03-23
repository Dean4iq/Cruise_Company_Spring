package model.service;

import exception.NoSuchIdException;
import model.dao.DaoFactory;
import model.dao.TicketDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum AdminSearchService {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(AdminSearchService.class);
    private DaoFactory daoFactory;
    private TicketDao ticketDao;

    AdminSearchService() {
        daoFactory = JDBCDaoFactory.getInstance();
    }

    public Ticket getTicketInfo(int ticketId) throws NoSuchIdException {
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
