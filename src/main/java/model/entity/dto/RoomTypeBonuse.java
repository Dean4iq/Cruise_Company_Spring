package model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "room_bonuses")
public class RoomTypeBonuse implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "room_type_rt_id")
    private RoomType roomType;
    @Id
    @ManyToOne
    @JoinColumn(name = "bonuses_bo_id")
    private Bonuse bonuse;

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Bonuse getBonuse() {
        return bonuse;
    }

    public void setBonuse(Bonuse bonuse) {
        this.bonuse = bonuse;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        RoomTypeBonuse that = (RoomTypeBonuse) object;
        return getRoomType().equals(that.getRoomType()) &&
                getBonuse().equals(that.getBonuse());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomType(), getBonuse());
    }

    @Override
    public String toString() {
        return "RoomTypeBonuse{" +
                '}';
    }
}
