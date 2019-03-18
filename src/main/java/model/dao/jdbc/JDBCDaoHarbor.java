package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.HarborDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.Harbor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoHarbor implements HarborDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoHarbor.class);
    private Connection connection;

    JDBCDaoHarbor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Harbor harbor) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(harbor.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, harbor.getName());
            preparedStatement.setInt(2, harbor.getCountryId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public Harbor findById(Integer id) throws NoSuchIdException {
        Harbor harbor = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Harbor.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                harbor = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (harbor == null) {
            throw new NoSuchIdException(id.toString());
        }

        return harbor;
    }

    static Harbor extractFromResultSet(ResultSet resultSet) throws SQLException {
        Harbor harbor = new Harbor();

        harbor.setId(resultSet.getInt("hb_id"));
        harbor.setName(resultSet.getString("harbor.name"));
        harbor.setCountryId(resultSet.getInt("country_co_id"));

        return harbor;
    }

    @Override
    public List<Harbor> findAll() {
        List<Harbor> harbors = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Harbor.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                harbors.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return harbors;
    }

    @Override
    public void update(Harbor harbor) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(harbor.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, harbor.getName());
            preparedStatement.setInt(2, harbor.getCountryId());
            preparedStatement.setInt(3, harbor.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Harbor harbor) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(harbor.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, harbor.getId());

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
