package model.dao;

import model.exception.NoSuchIdException;
import model.entity.dto.Ticket;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {
    int createAndReturnId(Ticket ticket);
    Ticket findById(Integer id) throws NoSuchIdException;
    Ticket findFullTicketInfo(Integer id) throws NoSuchIdException;
    List<Ticket> getTicketsForCruise(Integer cruiseId);
}
