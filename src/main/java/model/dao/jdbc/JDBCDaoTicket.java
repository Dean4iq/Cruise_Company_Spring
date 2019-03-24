package model.dao.jdbc;

import model.exception.AnnotationAbsenceException;
import model.exception.NoSuchIdException;
import model.dao.TicketDao;
import model.dao.sql.SQLScripts;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Ticket findFullTicketInfo(Integer id) throws NoSuchIdException {
        Ticket ticket = null;
        Map<Integer, Ticket> ticketMap = new HashMap<>();
        Map<Integer, Room> roomMap = new HashMap<>();
        Map<Integer, RoomType> roomTypeMap = new HashMap<>();
        Map<Integer, Cruise> cruiseMap = new HashMap<>();
        Map<Integer, Ship> shipMap = new HashMap<>();
        Map<String, User> userMap = new HashMap<>();

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SQLScripts.INSTANCE.FIND_TICKET_FULL_INFO)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ticket = extractFromResultSet(resultSet);
                Room room = JDBCDaoRoom.extractFromResultSet(resultSet);
                RoomType roomType = JDBCDaoRoomType.extractFromResultSet(resultSet);
                Bonuse bonuse = JDBCDaoBonuse.extractFromResultSet(resultSet);
                Cruise cruise = JDBCDaoCruise.extractFromResultSet(resultSet);
                Ship ship = JDBCDaoShip.extractFromResultSet(resultSet);
                User user = JDBCDaoUser.extractFromResultSet(resultSet);

                room.setRoomType(makeUniqueRoomType(roomTypeMap, roomType));
                cruise.setShip(makeUniqueShip(shipMap, ship));
                room.setShip(cruise.getShip());

                ticket = makeUniqueTicket(ticketMap, ticket);
                ticket.setRoom(makeUniqueRoom(roomMap, room));
                ticket.setUser(makeUniqueUser(userMap, user));
                ticket.setCruise(makeUniqueCruise(cruiseMap, cruise));

                ticket.getRoom().getRoomType().getBonuses().add(bonuse);
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
        if (ticket == null) {
            throw new NoSuchIdException(id.toString());
        }

        return ticket;
    }

    private Ticket makeUniqueTicket(Map<Integer, Ticket> ticketMap, Ticket ticket) {
        ticketMap.putIfAbsent(ticket.getId(), ticket);

        return ticketMap.get(ticket.getId());
    }

    private User makeUniqueUser(Map<String, User> userMap, User user) {
        userMap.putIfAbsent(user.getLogin(), user);

        return userMap.get(user.getLogin());
    }

    private Room makeUniqueRoom(Map<Integer, Room> roomMap, Room room) {
        roomMap.putIfAbsent(room.getId(), room);

        return roomMap.get(room.getId());
    }

    private RoomType makeUniqueRoomType(Map<Integer, RoomType> roomTypeMap, RoomType roomType) {
        roomTypeMap.putIfAbsent(roomType.getId(), roomType);

        return roomTypeMap.get(roomType.getId());
    }

    private Cruise makeUniqueCruise(Map<Integer, Cruise> cruiseMap, Cruise cruise) {
        cruiseMap.putIfAbsent(cruise.getId(), cruise);

        return cruiseMap.get(cruise.getId());
    }

    private Ship makeUniqueShip(Map<Integer, Ship> shipMap, Ship ship) {
        shipMap.putIfAbsent(ship.getId(), ship);

        return shipMap.get(ship.getId());
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
                .purchaseDate(resultSet.getTimestamp("ticket.purchase_date"))
                .price(resultSet.getInt("ticket.price"))
                .login(resultSet.getString("ticket.user_login"))
                .roomId(resultSet.getInt("ticket.room_ro_id"))
                .cruiseId(resultSet.getInt("ticket.cruise_cr_id"))
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
