package model.dao;

import exception.NoSuchIdException;
import model.entity.Ticket;

public interface TicketDao extends GenericDao<Ticket> {
    Ticket findById(Integer id) throws NoSuchIdException;
}
