package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Route;

public interface RouteDao extends GenericDao<Route> {
    Route findById(Route id) throws NoSuchIdException;
}
