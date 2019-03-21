package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Excursion;

import java.util.List;

public interface ExcursionDao extends GenericDao<Excursion> {
    Excursion findById(Integer id) throws NoSuchIdException;
    List<Excursion> getExcursionsForCruise(Integer cruiseId);
}
