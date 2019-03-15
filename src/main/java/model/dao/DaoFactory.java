package model.dao;

import model.dao.jdbc.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract CountryDao createCountryDao();

    public abstract CruiseDao createCruiseDao();

    public abstract ExcursionDao createExcursionDao();

    public abstract HarborDao createHarborDao();

    public abstract RoomDao createRoomDao();

    public abstract RouteDao createRouteDao();

    public abstract ShipDao createShipDao();

    public abstract TicketDao createTicketDao();

    public abstract UserDao createUserDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory temp = new JDBCDaoFactory();
                    daoFactory = temp;
                }
            }
        }
        return daoFactory;
    }
}
