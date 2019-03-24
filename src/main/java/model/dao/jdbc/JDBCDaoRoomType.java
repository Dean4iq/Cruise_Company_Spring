package model.dao.jdbc;

import model.exception.AnnotationAbsenceException;
import model.exception.NoSuchIdException;
import model.dao.RoomTypeDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.RoomType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoRoomType implements RoomTypeDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoRoomType.class);
    private Connection connection;

    JDBCDaoRoomType(Connection connection) {
        this.connection = connection;
    }

    @Override
    public RoomType findById(Integer id) throws NoSuchIdException {
        RoomType roomType = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(RoomType.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomType = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (roomType == null) {
            throw new NoSuchIdException(id.toString());
        }

        return roomType;
    }

    static RoomType extractFromResultSet(ResultSet resultSet) throws SQLException{
        RoomType roomType = new RoomType();

        roomType.setId(resultSet.getInt("rt_id"));
        roomType.setName(resultSet.getString("room_type.name"));
        roomType.setPriceModifier(resultSet.getInt("room_type.cost_modifier"));

        return roomType;
    }

    @Override
    public void create(RoomType roomType) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(roomType.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, roomType.getName());
            preparedStatement.setInt(2, roomType.getPriceModifier());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public List<RoomType> findAll() {
        List<RoomType> roomTypeList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(RoomType.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomTypeList.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return roomTypeList;
    }

    @Override
    public void update(RoomType roomType) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(roomType.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, roomType.getName());
            preparedStatement.setInt(2, roomType.getPriceModifier());
            preparedStatement.setInt(3, roomType.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(RoomType roomType) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(roomType.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, roomType.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void close() throws Exception {
        ConnectorDB.INSTANCE.returnConnectionToPool(connection);
    }
}
