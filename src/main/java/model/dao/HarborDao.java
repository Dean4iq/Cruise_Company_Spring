package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Harbor;

public interface HarborDao extends GenericDao<Harbor> {
    Harbor findById(Integer id) throws NoSuchIdException;
}
