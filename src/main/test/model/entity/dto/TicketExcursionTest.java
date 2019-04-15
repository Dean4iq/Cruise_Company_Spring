package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketExcursionTest {
    private TicketExcursion ticketExcursion;

    private int ticketId = 45;
    private int excursionId = 33;

    @Before
    public void setUp() throws Exception {
        ticketExcursion = new TicketExcursion();

        ticketExcursion.setTicketId(ticketId);
        ticketExcursion.setExcursionId(excursionId);
    }

    @Test
    public void getTicketId() {
        assertEquals(ticketId, ticketExcursion.getTicketId());
    }

    @Test
    public void setTicketId() {
        ticketId = 34;

        ticketExcursion.setTicketId(ticketId);

        assertEquals(ticketId, ticketExcursion.getTicketId());
    }

    @Test
    public void getExcursionId() {
        assertEquals(excursionId, ticketExcursion.getExcursionId());
    }

    @Test
    public void setExcursionId() {
        excursionId = 98;

        ticketExcursion.setExcursionId(excursionId);

        assertEquals(excursionId, ticketExcursion.getExcursionId());
    }

    @Test
    public void equals() {
        assertEquals(ticketExcursion, ticketExcursion);
    }
}