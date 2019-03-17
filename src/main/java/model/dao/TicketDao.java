package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Ticket;

public interface TicketDao extends GenericDao<Ticket> {
    Ticket findById(Integer id) throws NoSuchIdException;
}
