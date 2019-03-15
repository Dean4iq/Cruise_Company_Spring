package model.dao;

import exception.NoSuchIdException;
import model.entity.Harbor;

public interface HarborDao extends GenericDao<Harbor> {
    Harbor findById(Integer id) throws NoSuchIdException;
}
