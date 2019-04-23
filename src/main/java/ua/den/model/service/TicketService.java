package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public void insertTicketInDB(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsForCruise(Long cruiseId) {
        return ticketRepository.findAllByCruiseId(cruiseId);
    }
}
