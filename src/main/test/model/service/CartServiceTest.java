package ua.den.model.service;

import ua.den.model.entity.dto.Cruise;
import ua.den.model.entity.dto.Excursion;
import ua.den.model.entity.dto.Room;
import ua.den.model.entity.dto.Ticket;
import ua.den.model.exception.NoSuchIdException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CartServiceTest {
    @Mock
    private CartService cartService = Mockito.mock(CartService.class);
    private Cruise cruise;
    private Room room;
    private Excursion excursion;
    private List<Excursion> excursionList;

    @Before
    public void setUp() throws Exception {
        cruise = new Cruise.Builder()
                .id(22)
                .name("Hawaiian holidays")
                .price(342)
                .date(new Timestamp(new Date().getTime()))
                .shipId(3).build();

        room = new Room();
        excursion = new Excursion();
        excursionList = new ArrayList<>();

        room.setId(82);
        room.setRoomTypeId(4);
        room.setShipId(1);

        excursion.setId(77);
        excursion.setPrice(53);
        excursion.setInformation("info.honolulu");
        excursion.setHarborId(44);

        for (int i = 0; i < 8; i++) {
            Excursion excursion = new Excursion();

            excursion.setId(i+60);
            excursion.setInformation("info");
            excursion.setPrice(150+5*i);
            excursion.setHarborId(66);

            excursionList.add(excursion);
        }

        Mockito.when(cartService.getCruiseInfo("22")).thenReturn(cruise);
        Mockito.when(cartService.getRoomInfo("82")).thenReturn(room);
        Mockito.when(cartService.getExcursionById(77)).thenReturn(excursion);
        Mockito.when(cartService.getExcursionList("33")).thenReturn(excursionList);
    }

    @Test
    public void getCruiseInfo() {
        assertEquals(cruise, cartService.getCruiseInfo("22"));
    }

    @Test
    public void getRoomInfo() {
        assertEquals(room, cartService.getRoomInfo("82"));
    }

    @Test
    public void getExcursionById() throws NoSuchIdException {
        assertEquals(excursion, cartService.getExcursionById(77));
    }

    @Test
    public void getExcursionList() {
        assertEquals(excursionList, cartService.getExcursionList("33"));
    }

    @Test
    public void applyTicketPurchasing() throws NoSuchIdException {
        cartService.applyTicketPurchasing(new Ticket.Builder().id(77).build(), excursionList);
    }
}