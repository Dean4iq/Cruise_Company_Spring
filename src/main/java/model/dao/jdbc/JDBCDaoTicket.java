package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.TicketDao;
import model.dao.sql.SQLScripts;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoTicket implements TicketDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoTicket.class);
    private Connection connection;

    JDBCDaoTicket(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Ticket ticket) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ticket.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setTimestamp(1, ticket.getPurchaseDate());
            preparedStatement.setInt(2, ticket.getPrice());
            preparedStatement.setString(3, ticket.getLogin());
            preparedStatement.setInt(4, ticket.getRoomId());
            preparedStatement.setInt(5, ticket.getCruiseId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public int createAndReturnId(Ticket ticket) {
        int id = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ticket.getClass(), SQLOperation.INSERT),
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, ticket.getPurchaseDate());
            preparedStatement.setInt(2, ticket.getPrice());
            preparedStatement.setString(3, ticket.getLogin());
            preparedStatement.setInt(4, ticket.getRoomId());
            preparedStatement.setInt(5, ticket.getCruiseId());

            id = preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return id;
    }

    @Override
    public Ticket findById(Integer id) throws NoSuchIdException {
        Ticket ticket = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Ticket.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ticket = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (ticket == null) {
            throw new NoSuchIdException(id.toString());
        }

        return ticket;
    }

    @Override
    public List<Ticket> getTicketsForCruise(Integer cruiseId) {
        List<Ticket> ticketList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SQLScripts.INSTANCE.FIND_TICKETS_FOR_CRUISE)) {
            preparedStatement.setInt(1, cruiseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ticketList.add(extractFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(e);
        }

        return ticketList;
    }

    static Ticket extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Ticket.Builder()
                .id(resultSet.getInt("ti_id"))
                .purchaseDate(resultSet.getTimestamp("purchase_date"))
                .price(resultSet.getInt("ticket.price"))
                .login(resultSet.getString("user_login"))
                .roomId(resultSet.getInt("room_ro_id"))
                .cruiseId(resultSet.getInt("cruise_cr_id"))
                .build();
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(Ticket.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tickets.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return tickets;
    }

    @Override
    public void update(Ticket ticket) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ticket.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setTimestamp(1, ticket.getPurchaseDate());
            preparedStatement.setInt(2, ticket.getPrice());
            preparedStatement.setString(3, ticket.getLogin());
            preparedStatement.setInt(4, ticket.getRoomId());
            preparedStatement.setInt(5, ticket.getCruiseId());
            preparedStatement.setInt(6, ticket.getId());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(Ticket ticket) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(ticket.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setInt(1, ticket.getId());

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
