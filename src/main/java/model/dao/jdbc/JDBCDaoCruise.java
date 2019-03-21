package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.CruiseDao;
import model.dao.sql.SQLScripts;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCDaoCruise implements CruiseDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoCruise.class);
    private Connection connection;

    public JDBCDaoCruise(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Cruise cruise) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(cruise.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, cruise.getName());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public Cruise findById(Integer id) throws NoSuchIdException {
        Cruise cruise = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Cruise.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cruise = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (cruise == null) {
            throw new NoSuchIdException(id.toString());
        }

        return cruise;
    }

    @Override
    public List<Cruise> findFullCruiseInfo() {
        List<Cruise> cruiseList = new ArrayList<>();
        Map<Integer, Cruise> cruiseMap = new HashMap<>();
        Map<Integer, Harbor> harborMap = new HashMap<>();
        Map<Integer, Country> countryMap = new HashMap<>();
        Map<Integer, Ship> shipMap = new HashMap<>();

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SQLScripts.INSTANCE.FIND_CRUISE_FULL_INFO)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Cruise cruise = makeUniqueCruise(cruiseMap, extractFromResultSet(resultSet));
                Route route = JDBCDaoRoute.extractFromResultSet(resultSet);
                Harbor harbor = makeUniqueHarbor(harborMap, JDBCDaoHarbor.extractFromResultSet(resultSet));
                Country country = makeUniqueCountry(countryMap, JDBCDaoCountry.extractFromResultSet(resultSet));
                Ship ship = makeUniqueShip(shipMap, JDBCDaoShip.extractFromResultSet(resultSet));

                cruise.getRouteList().add(route);
                cruise.setShip(ship);
                route.setCruise(cruise);
                route.setHarbor(harbor);
                harbor.setCountry(country);

                if (!cruiseList.contains(cruise)) {
                    cruiseList.add(cruise);
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
        }

        return cruiseList;
    }

    private Cruise makeUniqueCruise(Map<Integer, Cruise> cruiseMap, Cruise cruise) {
        cruiseMap.putIfAbsent(cruise.getId(), cruise);

        return cruiseMap.get(cruise.getId());
    }

    private Harbor makeUniqueHarbor(Map<Integer, Harbor> harborMap, Harbor harbor) {
        harborMap.putIfAbsent(harbor.getId(), harbor);

        return harborMap.get(harbor.getId());
    }

    private Country makeUniqueCountry(Map<Integer, Country> countryMap, Country country) {
        countryMap.putIfAbsent(country.getId(), country);

        return countryMap.get(country.getId());
    }

    private Ship makeUniqueShip(Map<Integer, Ship> shipMap, Ship ship) {
        shipMap.putIfAbsent(ship.getId(), ship);

        return shipMap.get(ship.getId());
    }

    static Cruise extractFromResultSet(ResultSet resultSet) throws SQLException {
        Cruise cruise = new Cruise();

        cruise.setId(resultSet.getInt("cr_id"));
        cruise.setName(resultSet.getString("cruise.name"));
        cruise.setPrice(resultSet.getInt("price"));
        cruise.setDate(resultSet.getTimestamp("date"));
        cruise.setShipId(resultSet.getInt("ship_sh_id"));

        return cruise;
    }

    @Override
    public List<Cruise> findAll() {
        List<Cruise> cruiseList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Cruise.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cruiseList.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return cruiseList;
    }

    @Override
    public void update(Cruise cruise) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(cruise.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, cruise.getName());
            preparedStatement.setInt(2, cruise.getPrice());
            preparedStatement.setTimestamp(3, cruise.getDate());
            preparedStatement.setInt(4, cruise.getShipId());
            preparedStatement.setInt(5, cruise.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Cruise cruise) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(cruise.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, cruise.getId());

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
