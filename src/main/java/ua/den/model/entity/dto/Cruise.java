package ua.den.model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cruise")
public class Cruise implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "cr_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
    @Column(name = "date")
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "ship_sh_id")
    private Ship ship;

    @OneToMany(mappedBy = "cruise")
    private List<Route> routeList;

    @Transient
    private int daysInRoute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public int getDaysInRoute() {
        return daysInRoute;
    }

    public void setDaysInRoute(int daysInRoute) {
        this.daysInRoute = daysInRoute;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Cruise cruise = (Cruise) object;
        return getId().equals(cruise.getId()) &&
                getPrice().equals(cruise.getPrice()) &&
                Objects.equals(getName(), cruise.getName()) &&
                Objects.equals(getDate(), cruise.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getDate());
    }

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", daysInRoute=" + daysInRoute;
    }
}
