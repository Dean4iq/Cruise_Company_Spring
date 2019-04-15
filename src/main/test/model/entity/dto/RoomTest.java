package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {
    private Room room;

    private int id = 111;
    private int roomTypeId = 3;
    private int shipId = 7;

    private RoomType roomType;
    private Ship ship;
    private int price = 333;
    private boolean available = false;

    @Before
    public void setUp() throws Exception {
        room = new Room();
        roomType = new RoomType();
        ship = new Ship();

        roomType.setId(3);
        ship.setId(7);

        room.setId(id);
        room.setRoomTypeId(roomTypeId);
        room.setShipId(shipId);

        room.setRoomType(roomType);
        room.setShip(ship);

        room.setPrice(price);
        room.setAvailable(available);
    }

    @Test
    public void getId() {
        assertEquals(id, room.getId());
    }

    @Test
    public void setId() {
        id = 8;

        room.setId(id);

        assertEquals(id, room.getId());
    }

    @Test
    public void getRoomTypeId() {
        assertEquals(roomTypeId, room.getRoomTypeId());
    }

    @Test
    public void setRoomTypeId() {
        roomTypeId = 2;

        room.setRoomTypeId(roomTypeId);

        assertEquals(roomTypeId, room.getRoomTypeId());
    }

    @Test
    public void getShipId() {
        assertEquals(shipId, room.getShipId());
    }

    @Test
    public void setShipId() {
        shipId = 222;

        room.setShipId(shipId);

        assertEquals(shipId, room.getShipId());
    }

    @Test
    public void getRoomType() {
        assertEquals(roomType, room.getRoomType());
    }

    @Test
    public void setRoomType() {
        RoomType roomTypeTest = new RoomType();
        roomTypeTest.setId(2);

        room.setRoomType(roomTypeTest);

        assertEquals(roomTypeTest, room.getRoomType());
    }

    @Test
    public void getShip() {
        assertEquals(ship, room.getShip());
    }

    @Test
    public void setShip() {
        Ship shipTest = new Ship();
        shipTest.setId(332);

        room.setShip(shipTest);

        assertEquals(shipTest, room.getShip());
    }

    @Test
    public void getPrice() {
        assertEquals(price, room.getPrice());
    }

    @Test
    public void setPrice() {
        price = 99;

        room.setPrice(price);

        assertEquals(price, room.getPrice());
    }

    @Test
    public void isAvailable() {
        assertEquals(available, room.isAvailable());
    }

    @Test
    public void setAvailable() {
        room.setAvailable(true);

        assertTrue(room.isAvailable());
    }

    @Test
    public void equals() {
        Room testRoom = new Room();

        testRoom.setId(room.getId());
        testRoom.setRoomTypeId(room.getRoomTypeId());
        testRoom.setShipId(room.getShipId());
        testRoom.setPrice(room.getPrice());

        assertEquals(room, testRoom);
    }
}