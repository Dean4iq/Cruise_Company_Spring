package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.TicketExcursionDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.TicketExcursion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoTicketExcursion implements TicketExcursionDao {
    public static final Logger LOG = LogManager.getLogger(JDBCDaoTicketExcursion.class);
    private Connection connection;

    JDBCDaoTicketExcursion(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TicketExcursion findById(TicketExcursion ticketExcursion) throws NoSuchIdException {
        TicketExcursion ticketExcursionFromDB = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(TicketExcursion.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, ticketExcursion.getTicketId());
            preparedStatement.setInt(2, ticketExcursion.getExcursionId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ticketExcursionFromDB = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (ticketExcursionFromDB == null) {
            throw new NoSuchIdException("Ticket id: " + ticketExcursion.getTicketId() +
                    "Excursion id: " + ticketExcursion.getExcursionId());
        }

        return ticketExcursionFromDB;
    }

    static TicketExcursion extractFromResultSet(ResultSet resultSet) throws SQLException {
        TicketExcursion ticketExcursion = new TicketExcursion();

        ticketExcursion.setTicketId(resultSet.getInt("ticket_ti_id"));
        ticketExcursion.setExcursionId(resultSet.getInt("excursion_exc_id"));

        return ticketExcursion;
    }

    @Override
    public void addList(List<TicketExcursion> ticketExcursionList) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(TicketExcursion.class, SQLOperation.INSERT))) {
            connection.setAutoCommit(false);

            for (TicketExcursion ticketExcursion : ticketExcursionList) {
                preparedStatement.setInt(1, ticketExcursion.getTicketId());
                preparedStatement.setInt(2, ticketExcursion.getExcursionId());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            connection.commit();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void create(TicketExcursion ticketExcursion) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ticketExcursion.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setInt(1, ticketExcursion.getTicketId());
            preparedStatement.setInt(2, ticketExcursion.getExcursionId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public List<TicketExcursion> findAll() {
        List<TicketExcursion> ticketExcursionList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(TicketExcursion.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ticketExcursionList.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return ticketExcursionList;
    }

    @Override
    public void update(TicketExcursion ticketExcursion) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(TicketExcursion ticketExcursion) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ticketExcursion.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, ticketExcursion.getTicketId());
            preparedStatement.setInt(2, ticketExcursion.getExcursionId());

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
