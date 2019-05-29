package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.exception.NoSuchIdException;
import ua.den.model.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTicketsForUser(String login) {
        return ticketRepository.findByUserLogin(login);
    }

    public Page<Ticket> getPaginatedTicketListForCruise(Long cruiseId, Integer page) {
        return ticketRepository.findByCruiseId(cruiseId, PageRequest.of(page, 10));
    }

    public Ticket getTicketById(Long id) throws NoSuchIdException {

        return ticketRepository.findById(id).orElseThrow(() -> new NoSuchIdException(Long.toString(id)));
    }

    public boolean checkTicketExistence(Ticket ticket) {
        return ticketRepository.findAll().stream().anyMatch(tick -> tick.equals(ticket));
    }

    public void insertTicketInDB(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsForCruise(Long cruiseId) {
        return ticketRepository.findAllByCruiseId(cruiseId);
    }
}
