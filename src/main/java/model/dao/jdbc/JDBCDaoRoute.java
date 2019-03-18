package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.RouteDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoRoute implements RouteDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoRoute.class);
    private Connection connection;

    JDBCDaoRoute(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Route route) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(route.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setInt(1, route.getCruiseId());
            preparedStatement.setInt(2, route.getHarborId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public Route findById(Route id) throws NoSuchIdException {
        Route route = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Route.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, route.getCruiseId());
            preparedStatement.setInt(2, route.getHarborId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                route = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (route == null) {
            throw new NoSuchIdException(id.toString());
        }

        return route;
    }

    static Route extractFromResultSet(ResultSet resultSet) throws SQLException {
        Route route = new Route();

        route.setCruiseId(resultSet.getInt("cruise_cr_id"));
        route.setHarborId(resultSet.getInt("harbor_hb_id"));
        route.setArrival(resultSet.getTimestamp("arrival"));
        route.setDeparture(resultSet.getTimestamp("departure"));

        return route;
    }

    @Override
    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Route.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                routes.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return routes;
    }

    @Override
    public void update(Route route) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(route.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setInt(1, route.getCruiseId());
            preparedStatement.setInt(2, route.getHarborId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Route route) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(route.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, route.getCruiseId());
            preparedStatement.setInt(2, route.getHarborId());

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
