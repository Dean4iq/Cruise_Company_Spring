package model.dao;

import exception.NoSuchIdException;
import model.entity.dto.RoomType;

public interface RoomTypeDao extends GenericDao<RoomType>{
    RoomType findById(Integer id) throws NoSuchIdException;
}
