package model.service;

import model.entity.dto.Cruise;
import model.entity.dto.Room;
import model.entity.dto.Ticket;
import model.exception.NoSuchIdException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CabinSelectionServiceTest {
    @Mock
    private CabinSelectionService cabinSelectionService = Mockito.mock(CabinSelectionService.class);
    private List<Room> roomList;
    private Cruise cruise;
    private List<Ticket> ticketList;

    @Before
    public void setUp() throws Exception {
        roomList = new ArrayList<>();
        ticketList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Room room = new Room();

            room.setId(i);
            room.setRoomTypeId(2);
            room.setShipId(3);

            roomList.add(room);
        }

        cruise = new Cruise.Builder()
                .id(22)
                .name("Hawaiian holidays")
                .price(342)
                .date(new Timestamp(new Date().getTime()))
                .shipId(3).build();

        for (int i = 0; i < 4; i++) {
            Ticket ticket = new Ticket.Builder()
                    .id(3)
                    .purchaseDate(new Timestamp(new Date().getTime()))
                    .price(6500)
                    .cruiseId(12)
                    .roomId(44)
                    .login("Stepanych").build();

            ticketList.add(ticket);
        }

        Mockito.when(cabinSelectionService.getCruiseLoadInfo("4")).thenReturn(roomList);
        Mockito.when(cabinSelectionService.getSearchedCruiseInfo("21")).thenThrow(new NoSuchIdException("21"));
        Mockito.when(cabinSelectionService.getSearchedCruiseInfo("22")).thenReturn(cruise);
        Mockito.when(cabinSelectionService.getTicketsForCruise("12")).thenReturn(ticketList);
    }

    @Test
    public void getCruiseLoadInfo() {
        assertEquals(roomList, cabinSelectionService.getCruiseLoadInfo("4"));
    }

    @Test
    public void getSearchedCruiseInfo() throws NoSuchIdException {
        assertEquals(cruise, cabinSelectionService.getSearchedCruiseInfo("22"));
    }

    @Test (expected = NoSuchIdException.class)
    public void getSearchedCruiseInfoException() throws NoSuchIdException {
        assertEquals(cruise, cabinSelectionService.getSearchedCruiseInfo("21"));
    }

    @Test
    public void getTicketsForCruise() {
        assertEquals(ticketList, cabinSelectionService.getTicketsForCruise("12"));
    }
}