package ua.den.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.den.model.entity.dto.Cruise;
import ua.den.model.entity.dto.Room;
import ua.den.model.entity.dto.Ticket;
import ua.den.model.repository.CruiseRepository;
import ua.den.model.repository.RoomRepository;
import ua.den.model.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class {@code CabinSelectionService} provides methods to receive data from DB for servlet commands
 *
 * @author Dean4iq
 * @version 1.0
 */
@Service
public class CabinSelectionService {
    private static final Logger LOG = LogManager.getLogger(CabinSelectionService.class);

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CruiseRepository cruiseRepository;
    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Receives and returns list of rooms on ship assigned for cruise
     *
     * @param cruiseId id of cruise to find ship with rooms
     * @return list of rooms on ship
     */
    public Page<Room> getCruiseLoadInfo(String cruiseId, int page, int elementsOnPage) {
        LOG.trace("getCruiseInfo({})", cruiseId);

        Pageable pageable = PageRequest.of(page, elementsOnPage);
        return roomRepository.findByCruise(Long.parseLong(cruiseId), pageable);
    }

    /**
     * Receives a cruise from DB and returns it to command
     *
     * @param cruiseId id of the cruise
     * @return cruise with filled fields
     */
    public Cruise getSearchedCruiseInfo(String cruiseId) {
        LOG.trace("getSearchedCruiseInfo({})", cruiseId);

        return cruiseRepository.getOne(Long.parseLong(cruiseId));
    }

    /**
     * Receives a list of tickets from DB for cruise and returns it to the command
     *
     * @param cruiseId id of cruise to find tickets for it
     * @return list of tickets for cruise
     */
    public List<Ticket> getTicketsForCruise(String cruiseId) {
        LOG.trace("getTicketsForCruise({})", cruiseId);

        return ticketRepository.findAllByCruiseId(Long.parseLong(cruiseId));
    }
}
