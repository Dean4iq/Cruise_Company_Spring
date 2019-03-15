package model.entity;

import java.util.List;

public class Cart {
    private Ticket ticket;
    private List<Excursion> excursionList;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Excursion> getExcursionList() {
        return excursionList;
    }

    public void setExcursionList(List<Excursion> excursionList) {
        this.excursionList = excursionList;
    }
}
