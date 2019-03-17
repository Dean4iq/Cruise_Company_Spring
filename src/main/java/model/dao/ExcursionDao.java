package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Excursion;

public interface ExcursionDao extends GenericDao<Excursion> {
    Excursion findById(Integer id) throws NoSuchIdException;
}
