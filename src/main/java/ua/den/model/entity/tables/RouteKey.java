package ua.den.model.entity.tables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RouteKey implements Serializable {
    @Column(name = "cruise_cr_id")
    private Long cruiseId;
    @Column(name="harbor_hb_id")
    private Long harborId;

    public Long getCruiseId() {
        return cruiseId;
    }

    public void setCruiseId(Long cruiseId) {
        this.cruiseId = cruiseId;
    }

    public Long getHarborId() {
        return harborId;
    }

    public void setHarborId(Long harborId) {
        this.harborId = harborId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteKey routeKey = (RouteKey) o;
        return Objects.equals(cruiseId, routeKey.cruiseId) &&
                Objects.equals(harborId, routeKey.harborId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cruiseId, harborId);
    }

    @Override
    public String toString() {
        return "RouteKey{" +
                "cruiseId=" + cruiseId +
                ", harborId=" + harborId +
                '}';
    }
}
