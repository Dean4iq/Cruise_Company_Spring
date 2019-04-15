package ua.den.model.entity.dto;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TicketExcursionKey implements Serializable {
    @Column(name = "ticket_ti_id")
    private Long ticketId;
    @Column(name = "excursion_exc_id")
    private Long excursionId;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(Long excursionId) {
        this.excursionId = excursionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketExcursionKey that = (TicketExcursionKey) o;
        return Objects.equals(ticketId, that.ticketId) &&
                Objects.equals(excursionId, that.excursionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, excursionId);
    }
}
