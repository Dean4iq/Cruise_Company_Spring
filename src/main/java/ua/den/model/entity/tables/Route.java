package ua.den.model.entity.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "route")
public class Route implements Serializable {
    @EmbeddedId
    private RouteKey routeKey;

    @ManyToOne
    @MapsId("cruise_cr_id")
    @JoinColumn(name = "cruise_cr_id")
    @JsonIgnore
    private Cruise cruise;

    @ManyToOne
    @MapsId("harbor_hb_id")
    @JoinColumn(name = "harbor_hb_id")
    private Harbor harbor;

    @Column(name = "arrival")
    private Timestamp arrival;

    @Column(name = "departure")
    private Timestamp departure;

    public RouteKey getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(RouteKey routeKey) {
        this.routeKey = routeKey;
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
        return getCruise().equals(route.getCruise()) &&
                getHarbor().equals(route.getHarbor()) &&
                Objects.equals(getArrival(), route.getArrival()) &&
                Objects.equals(getDeparture(), route.getDeparture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCruise(), getHarbor(), getArrival(), getDeparture());
    }

    @Override
    public String toString() {
        return "Route{" +
                ", arrival=" + arrival +
                ", departure=" + departure +
                '}';
    }
}
