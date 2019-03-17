package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.CountryDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoCountry implements CountryDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoCountry.class);
    private Connection connection;
    private SqlReflector sqlReflector = new SqlReflector();

    public JDBCDaoCountry(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Country country) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(country.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, country.getName());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public Country findById(Integer id) throws NoSuchIdException {
        Country country = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Country.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                country = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (country == null) {
            throw new NoSuchIdException(id.toString());
        }

        return country;
    }

    static Country extractFromResultSet(ResultSet resultSet) throws SQLException {
        Country country = new Country();

        country.setId(resultSet.getInt("co_id"));
        country.setName(resultSet.getString("name"));

        return country;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Country.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                countries.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return countries;
    }

    @Override
    public void update(Country country) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(country.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, country.getName());
            preparedStatement.setInt(2, country.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Country country) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(country.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, country.getId());

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
