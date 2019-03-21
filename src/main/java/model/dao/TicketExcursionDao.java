package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.TicketExcursion;

import java.util.List;

public interface TicketExcursionDao extends GenericDao<TicketExcursion> {
    TicketExcursion findById(TicketExcursion ticketExcursion) throws NoSuchIdException;
    void addList(List<TicketExcursion> ticketExcursionList);
}
