package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Cruise;

import java.util.List;

public interface CruiseDao extends GenericDao<Cruise> {
    Cruise findById(Integer id) throws NoSuchIdException;
    List<Cruise> findFullCruiseInfo();
}
