package model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class TicketTest {
    private Ticket ticket;

    private int id = 6;
    private Timestamp purchaseDate = new Timestamp(new Date().getTime());
    private int price = 77;
    private String login = "moreman";
    private int roomId = 4;
    private int cruiseId = 1;

    private User user;
    private Room room;
    private Cruise cruise;

    @Before
    public void setUp() throws Exception {
        user = new User.Builder()
                .login(login)
                .password("qwerty")
                .email("somemail@gmail.com")
                .name("Name")
                .surname("Surname")
                .admin(false).build();

        cruise = new Cruise.Builder()
                .id(cruiseId)
                .name("Somename")
                .date(new Timestamp( new Date().getTime()))
                .price(234)
                .shipId(11).build();

        room = new Room();
        room.setId(roomId);
        room.setShipId(11);
        room.setRoomTypeId(33);
        room.setPrice(12);

        ticket = new Ticket.Builder()
                .id(id)
                .purchaseDate(purchaseDate)
                .price(price)
                .login(login)
                .roomId(roomId)
                .cruiseId(cruiseId)
                .user(user)
                .room(room)
                .cruise(cruise).build();
    }

    @Test
    public void getId() {
        assertEquals(id, ticket.getId());
    }

    @Test
    public void setId() {
        id =2;

        ticket.setId(id);

        assertEquals(id, ticket.getId());
    }

    @Test
    public void getPurchaseDate() {
        assertEquals(purchaseDate, ticket.getPurchaseDate());
    }

    @Test
    public void setPurchaseDate() {
        purchaseDate = new Timestamp(new Date().getTime());

        ticket.setPurchaseDate(purchaseDate);

        assertEquals(purchaseDate, ticket.getPurchaseDate());
    }

    @Test
    public void getPrice() {
        assertEquals(price, ticket.getPrice());
    }

    @Test
    public void setPrice() {
        price = 111;

        ticket.setPrice(price);

        assertEquals(price, ticket.getPrice());
    }

    @Test
    public void getLogin() {
        assertEquals(login, ticket.getLogin());
    }

    @Test
    public void setLogin() {
        login = "Excelsior";

        ticket.setLogin(login);

        assertEquals(login, ticket.getLogin());
    }

    @Test
    public void getRoomId() {
        assertEquals(roomId, ticket.getRoomId());
    }

    @Test
    public void setRoomId() {
        roomId = 56;

        ticket.setRoomId(roomId);

        assertEquals(roomId, ticket.getRoomId());
    }

    @Test
    public void getCruiseId() {
        assertEquals(cruiseId, ticket.getCruiseId());
    }

    @Test
    public void setCruiseId() {
        cruiseId = 22;

        ticket.setCruiseId(cruiseId);

        assertEquals(cruiseId, ticket.getCruiseId());
    }

    @Test
    public void getUser() {
        assertEquals(user, ticket.getUser());
    }

    @Test
    public void setUser() {
        User user = new User.Builder()
                .login("Shkolnik228")
                .password("qwerty123456").build();

        ticket.setUser(user);

        assertEquals(user, ticket.getUser());
    }

    @Test
    public void getRoom() {
        assertEquals(room, ticket.getRoom());
    }

    @Test
    public void setRoom() {
        Room room = new Room();
        room.setId(543);

        ticket.setRoom(room);

        assertEquals(room, ticket.getRoom());
    }

    @Test
    public void getCruise() {
        assertEquals(cruise, ticket.getCruise());
    }

    @Test
    public void setCruise() {
        Cruise cruise = new Cruise.Builder()
                .id(65)
                .name("Red sea").build();

        ticket.setCruise(cruise);

        assertEquals(cruise, ticket.getCruise());
    }

    @Test
    public void equals() {
        assertEquals(ticket, ticket);
    }
}