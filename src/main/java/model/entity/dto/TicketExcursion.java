package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;

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
}
