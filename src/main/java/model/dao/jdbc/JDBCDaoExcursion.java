package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.ExcursionDao;
import model.dao.sql.SQLScripts;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.Country;
import model.entity.dto.Excursion;
import model.entity.dto.Harbor;
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

public class JDBCDaoExcursion implements ExcursionDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoExcursion.class);
    private Connection connection;

    public JDBCDaoExcursion(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Excursion excursion) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(excursion.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, excursion.getInformation());
            preparedStatement.setInt(2, excursion.getPrice());
            preparedStatement.setInt(3, excursion.getHarborId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public Excursion findById(Integer id) throws NoSuchIdException {
        Excursion excursion = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Excursion.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                excursion = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (excursion == null) {
            throw new NoSuchIdException(id.toString());
        }

        return excursion;
    }

    @Override
    public List<Excursion> getExcursionsForCruise(Integer cruiseId) {
        List<Excursion> excursionList = new ArrayList<>();
        Map<Integer, Harbor> harborMap = new HashMap<>();
        Map<Integer, Country> countryMap = new HashMap<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(
                SQLScripts.INSTANCE.FIND_EXCURSION_LIST_FOR_CRUISE)) {
            preparedStatement.setInt(1, cruiseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Excursion excursion = extractFromResultSet(resultSet);
                Harbor harbor = JDBCDaoHarbor.extractFromResultSet(resultSet);
                Country country = JDBCDaoCountry.extractFromResultSet(resultSet);

                harbor.setCountry(makeUniqueCountry(countryMap, country));
                excursion.setHarbor(makeUniqueHarbor(harborMap, harbor));
                excursionList.add(excursion);
            }
        } catch (SQLException e) {
            LOG.error(e);
        }

        return excursionList;
    }

    private Harbor makeUniqueHarbor(Map<Integer, Harbor> harborMap, Harbor harbor) {
        harborMap.putIfAbsent(harbor.getId(), harbor);

        return harborMap.get(harbor.getId());
    }

    private Country makeUniqueCountry(Map<Integer, Country> countryMap, Country country) {
        countryMap.putIfAbsent(country.getId(), country);

        return countryMap.get(country.getId());
    }

    static Excursion extractFromResultSet(ResultSet resultSet) throws SQLException {
        Excursion excursion = new Excursion();

        excursion.setId(resultSet.getInt("exc_id"));
        excursion.setInformation(resultSet.getString("excursion.info"));
        excursion.setPrice(resultSet.getInt("excursion.price"));
        excursion.setHarborId(resultSet.getInt("harbor_hb_id"));

        return excursion;
    }

    @Override
    public List<Excursion> findAll() {
        List<Excursion> excursionList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Excursion.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                excursionList.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return excursionList;
    }

    @Override
    public void update(Excursion excursion) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(excursion.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, excursion.getInformation());
            preparedStatement.setInt(2, excursion.getPrice());
            preparedStatement.setInt(3, excursion.getHarborId());
            preparedStatement.setInt(4, excursion.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Excursion excursion) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(excursion.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, excursion.getId());

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
