package model.dao.jdbc;

import model.exception.AnnotationAbsenceException;
import model.exception.NoSuchIdException;
import model.dao.BonuseDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.Bonuse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoBonuse implements BonuseDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoBonuse.class);
    private Connection connection;

    JDBCDaoBonuse(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Bonuse findById(Integer id) throws NoSuchIdException {
        Bonuse bonuse = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Bonuse.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bonuse = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (bonuse == null) {
            throw new NoSuchIdException(id.toString());
        }

        return bonuse;
    }

    @Override
    public void create(Bonuse bonuse) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(bonuse.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, bonuse.getName());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    static Bonuse extractFromResultSet(ResultSet resultSet) throws SQLException {
        Bonuse bonuse = new Bonuse();

        bonuse.setId(resultSet.getInt("bo_id"));
        bonuse.setName(resultSet.getString("bonuses.name"));

        return bonuse;
    }

    @Override
    public List<Bonuse> findAll() {
        List<Bonuse> bonuseList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Bonuse.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bonuseList.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return bonuseList;
    }

    @Override
    public void update(Bonuse bonuse) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(bonuse.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, bonuse.getName());
            preparedStatement.setInt(2, bonuse.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Bonuse bonuse) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(bonuse.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, bonuse.getId());

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
