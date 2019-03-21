package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.RoomDao;
import model.dao.sql.SQLScripts;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.Room;
import model.entity.dto.RoomType;
import model.entity.dto.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoRoom implements RoomDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoRoom.class);
    private Connection connection;

    JDBCDaoRoom(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Room room) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(room.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setInt(1, room.getRoomTypeId());
            preparedStatement.setInt(2, room.getShipId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public Room findById(Integer id) throws NoSuchIdException {
        Room room = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Room.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                room = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (room == null) {
            throw new NoSuchIdException(id.toString());
        }

        return room;
    }

    @Override
    public List<Room> findByCruise(Integer cruiseId) {
        List<Room> roomList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                SQLScripts.INSTANCE.FIND_SEAT_FOR_CRUISE_INFO)) {
            preparedStatement.setInt(1, cruiseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Room room = extractFromResultSet(resultSet);
                Ship ship = JDBCDaoShip.extractFromResultSet(resultSet);
                RoomType roomType = JDBCDaoRoomType.extractFromResultSet(resultSet);

                room.setRoomType(roomType);
                room.setShip(ship);
                roomList.add(room);
            }
        } catch (SQLException e) {
            LOG.error(e);
        }

        return roomList;
    }

    @Override
    public Room getFullInfo(Integer id) {
        Room room = null;

        try(PreparedStatement preparedStatement = connection
                .prepareStatement(SQLScripts.INSTANCE.FIND_ROOM_INFO)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                room = extractFromResultSet(resultSet);
                RoomType roomType = JDBCDaoRoomType.extractFromResultSet(resultSet);

                room.setRoomType(roomType);
            }
        } catch (SQLException e) {
            LOG.error(e);
        }

        return room;
    }

    static Room extractFromResultSet(ResultSet resultSet) throws SQLException {
        Room room = new Room();

        room.setId(resultSet.getInt("ro_id"));
        room.setRoomTypeId(resultSet.getInt("room_type_rt_id"));
        room.setShipId(resultSet.getInt("ship_sh_id"));

        return room;
    }

    @Override
    public List<Room> findAll() {
        List<Room> roomList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Room.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomList.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return roomList;
    }

    @Override
    public void update(Room room) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(room.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setInt(1, room.getRoomTypeId());
            preparedStatement.setInt(2, room.getShipId());
            preparedStatement.setInt(3, room.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Room room) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(room.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, room.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void close() {
        ConnectorDB.INSTANCE.returnConnectionToPool(connection);
    }
}
