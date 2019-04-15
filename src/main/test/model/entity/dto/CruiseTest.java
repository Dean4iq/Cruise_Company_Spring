package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CruiseTest {
    private Cruise cruise;

    private int id = 1;
    private String name = "Nordic Sea";
    private int price = 138;
    private Timestamp date = new Timestamp(new Date().getTime());
    private int shipId = 56;

    private int daysInRoute = 22;
    private Ship ship;
    private List<Route> routeList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        cruise = new Cruise.Builder()
                .id(id)
                .name(name)
                .price(price)
                .date(date)
                .shipId(shipId).build();

        ship = new Ship();
        ship.setId(2);
        ship.setName("Bismarck");

        cruise.setDaysInRoute(daysInRoute);
        cruise.setShip(ship);
        cruise.setRouteList(routeList);
    }

    @Test
    public void getId() {
        assertEquals(id, cruise.getId());
    }

    @Test
    public void setId() {
        id = 44;

        cruise.setId(id);

        assertEquals(id, cruise.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, cruise.getName());
    }

    @Test
    public void setName() {
        name = "Discover Bahamas";

        cruise.setName(name);

        assertEquals(name, cruise.getName());
    }

    @Test
    public void getPrice() {
        assertEquals(price, cruise.getPrice());
    }

    @Test
    public void setPrice() {
        price = 222;

        cruise.setPrice(price);

        assertEquals(price, cruise.getPrice());
    }

    @Test
    public void getDate() {
        assertEquals(date, cruise.getDate());
    }

    @Test
    public void setDate() {
        date = new Timestamp(new Date().getTime());

        cruise.setDate(date);

        assertEquals(date, cruise.getDate());
    }

    @Test
    public void getShipId() {
        assertEquals(shipId, cruise.getShipId());
    }

    @Test
    public void setShipId() {
        shipId = 77;

        cruise.setShipId(shipId);

        assertEquals(shipId, cruise.getShipId());
    }

    @Test
    public void getShip() {
        assertEquals(ship, cruise.getShip());
    }

    @Test
    public void setShip() {
        ship = new Ship();
        ship.setId(23);
        ship.setName("Washington");

        cruise.setShip(ship);

        assertEquals(ship, cruise.getShip());
    }

    @Test
    public void getRouteList() {
        assertEquals(routeList, cruise.getRouteList());
    }

    @Test
    public void setRouteList() {
        routeList = new ArrayList<>();
        routeList.add(new Route());

        cruise.setRouteList(routeList);

        assertEquals(routeList, cruise.getRouteList());
    }

    @Test
    public void getDaysInRoute() {
        assertEquals(daysInRoute, cruise.getDaysInRoute());
    }

    @Test
    public void setDaysInRoute() {
        daysInRoute = 9;

        cruise.setDaysInRoute(daysInRoute);

        assertEquals(daysInRoute, cruise.getDaysInRoute());
    }

    @Test
    public void equals() {
        Cruise testCruise = new Cruise.Builder()
                .id(cruise.getId())
                .name(cruise.getName())
                .price(cruise.getPrice())
                .date(cruise.getDate())
                .shipId(cruise.getShipId()).build();

        assertEquals(testCruise, cruise);
    }
}