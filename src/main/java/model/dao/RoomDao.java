package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.Room;

public interface RoomDao extends GenericDao<Room> {
    Room findById(Integer id) throws NoSuchIdException;
}
