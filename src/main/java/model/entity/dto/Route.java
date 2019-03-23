package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@TableName(name = "route")
public class Route implements Serializable {
    @TableField(name = "cruise_cr_id", primaryKey = true)
    private int cruiseId;
    @TableField(name = "harbor_hb_id", primaryKey = true)
    private int harborId;
    @TableField(name = "arrival")
    private Timestamp arrival;
    @TableField(name = "departure")
    private Timestamp departure;

    private Cruise cruise;
    private Harbor harbor;

    public int getCruiseId() {
        return cruiseId;
    }

    public void setCruiseId(int cruiseId) {
        this.cruiseId = cruiseId;
    }

    public int getHarborId() {
        return harborId;
    }

    public void setHarborId(int harborId) {
        this.harborId = harborId;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;
    }

    public Timestamp getArrival() {
        return arrival;
    }

    public void setArrival(Timestamp arrival) {
        this.arrival = arrival;
    }

    public Timestamp getDeparture() {
        return departure;
    }

    public void setDeparture(Timestamp departure) {
        this.departure = departure;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Route route = (Route) object;
        return getCruiseId() == route.getCruiseId() &&
                getHarborId() == route.getHarborId() &&
                Objects.equals(getArrival(), route.getArrival()) &&
                Objects.equals(getDeparture(), route.getDeparture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCruiseId(), getHarborId(), getArrival(), getDeparture());
    }

    @Override
    public String toString() {
        return "Route{" +
                "cruiseId=" + cruiseId +
                ", harborId=" + harborId +
                ", arrival=" + arrival +
                ", departure=" + departure +
                '}';
    }
}
