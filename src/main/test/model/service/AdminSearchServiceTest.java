package ua.den.model.service;

import ua.den.model.exception.NoSuchIdException;
import ua.den.model.entity.tables.Cruise;
import ua.den.model.entity.tables.Room;
import ua.den.model.entity.tables.Ship;
import ua.den.model.entity.tables.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AdminSearchServiceTest {
    @Mock
    private AdminSearchService adminSearchService = Mockito.mock(AdminSearchService.class);
    private Ticket ticket;

    @Before
    public void setUp() throws Exception {
        Room room = new Room();
        Ship ship = new Ship();

        room.setId(54);
        room.setShipId(8);
        room.setRoomTypeId(3);
        room.setShip(ship);

        ship.setId(8);
        ship.setCrewNumber(123);
        ship.setName("Europa");

        Cruise cruise = new Cruise.Builder()
                .id(6)
                .name("Nordic Seas")
                .price(1233)
                .shipId(8)
                .date(new Timestamp(new Date().getTime())).build();
        cruise.setShip(ship);

        ticket = new Ticket.Builder()
                .id(2)
                .purchaseDate(new Timestamp(new Date().getTime()))
                .price(234)
                .login("user1111")
                .roomId(54)
                .cruiseId(6)
                .cruise(cruise)
                .room(room).build();

        Mockito.when(adminSearchService.getTicketInfo(4)).thenThrow(new NoSuchIdException("4"));
        Mockito.when(adminSearchService.getTicketInfo(2)).thenReturn(ticket);
    }

    @Test(expected = NoSuchIdException.class)
    public void getTicketInfoException() throws NoSuchIdException {
        adminSearchService.getTicketInfo(4);
    }

    @Test
    public void getTicketInfo() throws NoSuchIdException {
        assertEquals(ticket, adminSearchService.getTicketInfo(2));
    }
}