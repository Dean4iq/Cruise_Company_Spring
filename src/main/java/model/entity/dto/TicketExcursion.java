package model.entity.dto;

import model.annotation.TableField;
import model.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(name = "ticket_excursion")
public class TicketExcursion implements Serializable {
    @TableField(name = "ticket_ti_id")
    private int ticketId;
    @TableField(name = "excursion_exc_id")
    private int excursionId;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
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
        return getTicketId() == that.getTicketId() &&
                getExcursionId() == that.getExcursionId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicketId(), getExcursionId());
    }

    @Override
    public String toString() {
        return "TicketExcursion{" +
                "ticketId=" + ticketId +
                ", excursionId=" + excursionId +
                '}';
    }
}
