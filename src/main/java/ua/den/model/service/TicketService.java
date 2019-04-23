package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.exception.NoSuchIdException;
import ua.den.model.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket getTicketById(Long id) throws NoSuchIdException {

        return ticketRepository.findById(id).orElseThrow(() -> new NoSuchIdException(Long.toString(id)));
    }

    public void insertTicketInDB(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsForCruise(Long cruiseId) {
        return ticketRepository.findAllByCruiseId(cruiseId);
    }
}
