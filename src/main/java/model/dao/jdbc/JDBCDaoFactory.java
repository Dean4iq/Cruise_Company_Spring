package model.dao.jdbc;

import model.dao.*;

import java.sql.Connection;

public class JDBCDaoFactory extends DaoFactory {
    private Connection getConnection() {
        return ConnectorDB.INSTANCE.getConnection();
    }

    @Override
    public CountryDao createCountryDao() {
        return new JDBCDaoCountry(getConnection());
    }

    @Override
    public CruiseDao createCruiseDao() {
        return new JDBCDaoCruise(getConnection());
    }

    @Override
    public ExcursionDao createExcursionDao() {
        return new JDBCDaoExcursion(getConnection());
    }

    @Override
    public HarborDao createHarborDao() {
        return new JDBCDaoHarbor(getConnection());
    }

    @Override
    public RoomDao createRoomDao() {
        return new JDBCDaoRoom(getConnection());
    }

    @Override
    public RoomTypeDao createRoomTypeDao() {
        return new JDBCDaoRoomType(getConnection());
    }

    @Override
    public RouteDao createRouteDao() {
        return new JDBCDaoRoute(getConnection());
    }

    @Override
    public ShipDao createShipDao() {
        return new JDBCDaoShip(getConnection());
    }

    @Override
    public TicketDao createTicketDao() {
        return new JDBCDaoTicket(getConnection());
    }

    @Override
    public TicketExcursionDao createTicketExcursionDao() {
        return new JDBCDaoTicketExcursion(getConnection());
    }

    @Override
    public UserDao createUserDao() {
        return new JDBCDaoUser(getConnection());
    }
}
