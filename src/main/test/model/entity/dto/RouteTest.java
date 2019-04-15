package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class RouteTest {
    private Route route;

    private int cruiseId = 8;
    private int harborId = 12;
    private Timestamp arrival = new Timestamp(new Date().getTime());
    private Timestamp departure = new Timestamp(new Date().getTime() + 5000);

    private Cruise cruise;
    private Harbor harbor;

    @Before
    public void setUp() throws Exception {
        route = new Route();

        cruise = new Cruise.Builder()
                .id(cruiseId)
                .name("Summer at Honolulu").build();
        harbor = new Harbor();
        harbor.setId(harborId);

        route.setCruiseId(cruiseId);
        route.setHarborId(harborId);
        route.setArrival(arrival);
        route.setDeparture(departure);
        route.setCruise(cruise);
        route.setHarbor(harbor);
    }

    @Test
    public void getCruiseId() {
        assertEquals(cruiseId, route.getCruiseId());
    }

    @Test
    public void setCruiseId() {
        cruiseId = 6;

        route.setCruiseId(cruiseId);

        assertEquals(cruiseId, route.getCruiseId());
    }

    @Test
    public void getHarborId() {
        assertEquals(harborId, route.getHarborId());
    }

    @Test
    public void setHarborId() {
        harborId = 82;

        route.setHarborId(harborId);

        assertEquals(harborId, route.getHarborId());
    }

    @Test
    public void getCruise() {
        assertEquals(cruise, route.getCruise());
    }

    @Test
    public void setCruise() {
        cruise = new Cruise.Builder().id(55).build();

        route.setCruise(cruise);

        assertEquals(cruise, route.getCruise());
    }

    @Test
    public void getHarbor() {
        assertEquals(harbor, route.getHarbor());
    }

    @Test
    public void setHarbor() {
        harbor = new Harbor();
        harbor.setId(333);

        route.setHarbor(harbor);

        assertEquals(harbor, route.getHarbor());
    }

    @Test
    public void getArrival() {
        assertEquals(arrival, route.getArrival());
    }

    @Test
    public void setArrival() {
        arrival = new Timestamp(new Date().getTime());

        route.setArrival(arrival);

        assertEquals(arrival, route.getArrival());
    }

    @Test
    public void getDeparture() {
        assertEquals(departure, route.getDeparture());
    }

    @Test
    public void setDeparture() {
        departure = new Timestamp(new Date().getTime());

        route.setDeparture(departure);

        assertEquals(departure, route.getDeparture());
    }

    @Test
    public void equals() {
        assertEquals(route, route);
    }
}