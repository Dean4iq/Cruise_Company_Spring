package model.service;

import model.dao.DaoFactory;
import model.dao.RoomDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.Room;

import java.util.ArrayList;
import java.util.List;

public enum CabinSelectionService {
    INSTANCE;

    private DaoFactory daoFactory;
    private RoomDao roomDao;

    CabinSelectionService() {
        daoFactory = JDBCDaoFactory.getInstance();
        roomDao = daoFactory.createRoomDao();
    }

    public List<Room> getCruiseLoadInfo(String cruiseId) {
        List<Room> roomList = new ArrayList<>();

        return roomList;
    }
}
