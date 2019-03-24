package model.dao;

import model.exception.NoSuchIdException;
import model.entity.dto.Room;

import java.util.List;

public interface RoomDao extends GenericDao<Room> {
    Room findById(Integer id) throws NoSuchIdException;
    List<Room> findByCruise(Integer cruiseId);
    Room getFullInfo(Integer id);
}
