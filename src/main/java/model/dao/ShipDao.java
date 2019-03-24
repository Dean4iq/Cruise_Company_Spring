package model.dao;

import model.exception.NoSuchIdException;
import model.entity.dto.Ship;

public interface ShipDao extends GenericDao<Ship> {
    Ship findById(Integer id) throws NoSuchIdException;
}
