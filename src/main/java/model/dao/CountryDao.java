package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Country;

public interface CountryDao extends GenericDao<Country> {
    Country findById(Integer id) throws NoSuchIdException;
}
