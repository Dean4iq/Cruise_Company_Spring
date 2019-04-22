package ua.den.model.service;

import org.springframework.web.context.annotation.SessionScope;
import ua.den.model.exception.NoSuchIdException;
import ua.den.model.entity.tables.*;
import ua.den.model.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code CartService} provides methods to receive and create data from/to DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
@Service
@SessionScope
public class CartService {
    private static final Logger LOG = LogManager.getLogger(CartService.class);

    @Autowired
    private CruiseRepository cruiseRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ExcursionRepository excursionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketExcursionRepository ticketExcursionRepository;

    /**
     * Receives and returns cruise info from DB
     *
     * @param cruiseId id of cruise to find
     * @return cruise from DB
     */
    public Cruise getCruiseInfo(Long cruiseId) {
        LOG.trace("getCruiseInfo({})", cruiseId);

        return cruiseRepository.getOne(cruiseId);
    }

    /**
     * Receives and returns room info from DB
     *
     * @param roomId id of room to find
     * @return room from DB
     */
    public Room getRoomInfo(String roomId) {
        LOG.trace("getRoomInfo({})", roomId);

        return roomRepository.getOne(Long.parseLong(roomId));
    }

    /**
     * Receives and returns excursion info from DB
     *
     * @param excursionId id of excursion to find
     * @return excursion from DB
     * @throws NoSuchIdException if excursion will not be found
     */
    public Excursion getExcursionById(Long excursionId) throws NoSuchIdException {
        LOG.trace("getExcursionById({})", excursionId);

        return excursionRepository.getOne(excursionId);
    }

    /**
     * Receives and returns list of excursion from DB for specified cruise
     *
     * @param cruiseId id of cruise to find available excursions
     * @return list of excursions from DB for cruise
     */
    public List<Excursion> getExcursionList(String cruiseId) {
        LOG.trace("getExcursionList({})", cruiseId);

        return excursionRepository.getExcursionsForCruise(Long.parseLong(cruiseId));
    }

    /**
     * Adds ticket and selected excursions from cart info to DB
     *
     * @param ticket     to be added
     * @param excursions list of excursions to be added to DB
     * @throws NoSuchIdException if ticket will be not found
     */
    public void applyTicketPurchasing(Ticket ticket, List<Excursion> excursions) throws NoSuchIdException {
        LOG.trace("applyTicketPurchasing()");
        Ticket addedTicket = addTicketInDB(ticket);

        if (excursions != null && !excursions.isEmpty()) {
            addExcursionsInDB(excursions, addedTicket);
        }
    }

    /**
     * Adds ticket in DB
     *
     * @param ticket to be added
     * @return id of row in DB after 'Create' ticket
     * @throws NoSuchIdException if created ticket row will be not found
     */
    private Ticket addTicketInDB(Ticket ticket) throws NoSuchIdException {
        LOG.trace("addTicketInDB()");

        return ticketRepository.save(ticket);

    }

    /**
     * Adds excursion in DB
     *
     * @param excursions from cart to be added
     * @param ticket   ticket to join with Ticket table in DB
     */
    private void addExcursionsInDB(List<Excursion> excursions, Ticket ticket) {
        LOG.trace("addExcursionsInDB()");
        List<TicketExcursion> ticketExcursionList = new ArrayList<>();

        excursions.forEach(excursion -> {
            TicketExcursion ticketExcursion = new TicketExcursion();

            ticketExcursion.setTicket(ticket);
            ticketExcursion.setExcursion(excursion);

            ticketExcursionList.add(ticketExcursion);
        });

        ticketExcursionRepository.saveAll(ticketExcursionList);
    }
}
