package model.dao;

import model.exception.NoSuchIdException;
import model.entity.dto.Bonuse;

public interface BonuseDao extends GenericDao<Bonuse> {
    Bonuse findById(Integer id) throws NoSuchIdException;
}
