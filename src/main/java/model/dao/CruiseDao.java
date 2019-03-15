package model.dao;

import exception.NoSuchIdException;
import model.entity.Cruise;

public interface CruiseDao extends GenericDao<Cruise> {
    Cruise findById(Integer id) throws NoSuchIdException;
}
