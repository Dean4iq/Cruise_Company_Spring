package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TableName(name = "cruise")
public class Cruise implements Serializable {
    @TableField(name = "cr_id", primaryKey = true)
    private int id;
    @TableField(name = "name")
    private String name;
    @TableField(name = "price")
    private int price;
    @TableField(name = "date")
    private Timestamp date;
    @TableField(name = "ship_sh_id")
    private int shipId;

    private int daysInRoute;
    private Ship ship;
    private List<Route> routeList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
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
        return getId() == cruise.getId() &&
                getPrice() == cruise.getPrice() &&
                getShipId() == cruise.getShipId() &&
                Objects.equals(getName(), cruise.getName()) &&
                Objects.equals(getDate(), cruise.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getDate(), getShipId());
    }

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", shipId=" + shipId +
                ", daysInRoute=" + daysInRoute;
    }
}
