package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.CruiseDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.Cruise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    Cruise extractFromResultSet(ResultSet resultSet) throws SQLException {
        Cruise cruise = new Cruise();

        cruise.setId(resultSet.getInt("cr_id"));
        cruise.setName(resultSet.getString("name"));
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
    public void close()  {
        ConnectorDB.INSTANCE.returnConnectionToPool(connection);
    }
}
