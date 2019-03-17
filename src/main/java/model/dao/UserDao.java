package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.User;

public interface UserDao extends GenericDao<User> {
    User findById(String id) throws NoSuchIdException;
}
