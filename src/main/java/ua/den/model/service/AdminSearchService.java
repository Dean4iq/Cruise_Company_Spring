package ua.den.model.service;

import ua.den.model.entity.dto.Ticket;
import ua.den.model.exception.NoSuchIdException;
import ua.den.model.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class {@code AdminSearchService} provides methods to receive data from DB for servlet commands
 *
 * @author Dean4iq
 * @version 1.0
 */
@Service
public class AdminSearchService {
    private static final Logger LOG = LogManager.getLogger(AdminSearchService.class);

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Receives and returns ticket data
     *
     * @param ticketId id of searched ticket
     * @return ticket with filled data from DB
     */
    public Ticket getTicketInfo(Long ticketId) throws NoSuchIdException {
        LOG.trace("getTicketInfo({})", ticketId);

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket == null) {
            throw new NoSuchIdException(Long.toString(ticketId));
        }

        return ticketRepository.getOne(ticketId);
    }
}