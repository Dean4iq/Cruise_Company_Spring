package model.dao;

import model.dao.jdbc.JDBCDaoFactory;

public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;

    public abstract BonuseDao createBonuseDao();

    public abstract CountryDao createCountryDao();

    public abstract CruiseDao createCruiseDao();

    public abstract ExcursionDao createExcursionDao();

    public abstract HarborDao createHarborDao();

    public abstract RoomDao createRoomDao();

    public abstract RoomTypeDao createRoomTypeDao();

    public abstract RouteDao createRouteDao();

    public abstract ShipDao createShipDao();

    public abstract TicketDao createTicketDao();

    public abstract TicketExcursionDao createTicketExcursionDao();

    public abstract UserDao createUserDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
