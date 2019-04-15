package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CartTest {
    private Cart cart;

    private Ticket ticket;
    private List<Excursion> excursionList;

    private int ticketId = 12;
    private int roomId = 13;
    private int cruiseId = 31;
    private String login = "userName";
    private int price = 493;
    private Timestamp purchaseDate = new Timestamp(new Date().getTime());

    @Before
    public void setUp() throws Exception {
        cart = new Cart();
        Excursion excursion = new Excursion();
        excursionList = new ArrayList<>();
        ticket = new Ticket.Builder()
                .id(ticketId)
                .roomId(roomId)
                .cruiseId(cruiseId)
                .login(login)
                .price(price)
                .purchaseDate(purchaseDate).build();

        excursion.setId(123);
        excursionList.add(excursion);

        cart.setTicket(ticket);
        cart.setExcursionList(excursionList);
    }

    @Test
    public void getTicket() {
        assertEquals(ticket, cart.getTicket());
    }

    @Test
    public void setTicket() {
        Ticket testTicket = new Ticket.Builder()
                .id(123)
                .roomId(11)
                .cruiseId(13)
                .login("Somebody")
                .price(222)
                .purchaseDate(purchaseDate).build();

        cart.setTicket(testTicket);

        assertEquals(testTicket, cart.getTicket());
    }

    @Test
    public void getExcursionList() {
        assertEquals(excursionList, cart.getExcursionList());
    }

    @Test
    public void setExcursionList() {
        Excursion excursion = new Excursion();
        excursionList = new ArrayList<>();
        excursionList.add(excursion);

        cart.setExcursionList(excursionList);

        assertEquals(excursionList, cart.getExcursionList());
    }
}