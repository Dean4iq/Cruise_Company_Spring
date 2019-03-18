package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.ShipDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
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

public class JDBCDaoShip implements ShipDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoShip.class);
    private Connection connection;

    JDBCDaoShip(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Ship ship) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ship.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, ship.getName());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public Ship findById(Integer id) throws NoSuchIdException {
        Ship ship = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Ship.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ship = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (ship == null) {
            throw new NoSuchIdException(id.toString());
        }

        return ship;
    }

    static Ship extractFromResultSet(ResultSet resultSet) throws SQLException {
        Ship ship = new Ship();

        ship.setId(resultSet.getInt("sh_id"));
        ship.setName(resultSet.getString("ship.name"));

        return ship;
    }

    @Override
    public List<Ship> findAll() {
        List<Ship> ships = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Ship.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ships.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return ships;
    }

    @Override
    public void update(Ship ship) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ship.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, ship.getName());
            preparedStatement.setInt(2, ship.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Ship ship) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ship.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, ship.getId());

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
