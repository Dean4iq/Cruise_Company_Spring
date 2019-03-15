package model.dao;

import exception.NoSuchIdException;
import model.entity.Ship;

public interface ShipDao extends GenericDao<Ship> {
    Ship findById(Integer id) throws NoSuchIdException;
}
