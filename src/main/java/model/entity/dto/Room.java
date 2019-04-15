package model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "room")
public class Room implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ro_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_type_rt_id")
    private RoomType roomType;
    @ManyToOne
    @JoinColumn(name = "ship_sh_id")
    private Ship ship;

    private int price;
    private boolean available = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Room room = (Room) object;
        return getId().equals(room.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                '}';
    }
}
