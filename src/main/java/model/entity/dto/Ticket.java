package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@TableName(name = "ticket")
public class Ticket implements Serializable {
    @TableField(name = "ti_id", primaryKey = true, autoincremented = true)
    private int id;
    @TableField(name = "purchase_date")
    private Timestamp purchaseDate;
    @TableField(name = "price")
    private int price;
    @TableField(name = "user_login")
    private String login;
    @TableField(name = "room_ro_id")
    private int roomId;
    @TableField(name = "cruise_cr_id")
    private int cruiseId;

    private User user;
    private Room room;
    private Cruise cruise;

    public Ticket(Builder builder) {
        this.id = builder.id;
        this.purchaseDate = builder.purchaseDate;
        this.price = builder.price;
        this.login = builder.login;
        this.roomId = builder.roomId;
        this.cruiseId = builder.cruiseId;

        this.user = builder.user;
        this.room = builder.room;
        this.cruise = builder.cruise;
    }

    public static class Builder {
        private int id;
        private Timestamp purchaseDate;
        private int price;
        private String login;
        private int roomId;
        private int cruiseId;

        private User user;
        private Room room;
        private Cruise cruise;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder purchaseDate(Timestamp purchaseDate) {
            this.purchaseDate = purchaseDate;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder login(String login) {
            this.login = login;
            return this;
        }

        public Builder roomId(int roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder cruiseId(int cruiseId) {
            this.cruiseId = cruiseId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder room(Room room) {
            this.room = room;
            return this;
        }

        public Builder cruise(Cruise cruise) {
            this.cruise = cruise;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCruiseId() {
        return cruiseId;
    }

    public void setCruiseId(int cruiseId) {
        this.cruiseId = cruiseId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) object;
        return getId() == ticket.getId() &&
                getPrice() == ticket.getPrice() &&
                getRoomId() == ticket.getRoomId() &&
                getCruiseId() == ticket.getCruiseId() &&
                Objects.equals(getPurchaseDate(), ticket.getPurchaseDate()) &&
                Objects.equals(getLogin(), ticket.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPurchaseDate(), getPrice(), getLogin(), getRoomId(), getCruiseId());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                ", login='" + login + '\'' +
                ", roomId=" + roomId +
                ", cruiseId=" + cruiseId +
                '}';
    }
}
