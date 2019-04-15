package ua.den.model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ticket_excursion")
public class TicketExcursion implements Serializable {
    @EmbeddedId
    private TicketExcursionKey ticketExcursionKey;

    @ManyToOne
    @MapsId("ticket_ti_id")
    @JoinColumn(name = "ticket_ti_id")
    private Ticket ticket;

    @ManyToOne
    @MapsId("excursion_exc_id")
    @JoinColumn(name = "excursion_exc_id")
    private Excursion excursion;

    public TicketExcursionKey getTicketExcursionKey() {
        return ticketExcursionKey;
    }

    public void setTicketExcursionKey(TicketExcursionKey ticketExcursionKey) {
        this.ticketExcursionKey = ticketExcursionKey;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Excursion getExcursion() {
        return excursion;
    }

    public void setExcursion(Excursion excursion) {
        this.excursion = excursion;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        TicketExcursion that = (TicketExcursion) object;
        return getTicket().equals(that.getTicket()) &&
                getExcursion().equals(that.getExcursion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicket(), getExcursion());
    }

    @Override
    public String toString() {
        return "TicketExcursion{" +
                '}';
    }
}
