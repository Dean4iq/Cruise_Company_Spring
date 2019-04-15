package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipTest {
    private Ship ship;

    private int id = 6;
    private String name = "Victory";
    private int crewNumber = 430;

    @Before
    public void setUp() throws Exception {
        ship = new Ship();

        ship.setId(id);
        ship.setName(name);
        ship.setCrewNumber(crewNumber);
    }

    @Test
    public void getId() {
        assertEquals(id, ship.getId());
    }

    @Test
    public void setId() {
        id = 12;

        ship.setId(id);

        assertEquals(id, ship.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, ship.getName());
    }

    @Test
    public void setName() {
        name = "Dome";

        ship.setName(name);

        assertEquals(name, ship.getName());
    }

    @Test
    public void getCrewNumber() {
        assertEquals(crewNumber, ship.getCrewNumber());
    }

    @Test
    public void setCrewNumber() {
        crewNumber = 1500;

        ship.setCrewNumber(crewNumber);

        assertEquals(crewNumber, ship.getCrewNumber());
    }

    @Test
    public void equals() {
        assertEquals(ship, ship);
    }

    @Test
    public void notEquals() {
        assertNotEquals(ship, new Ship());
    }
}