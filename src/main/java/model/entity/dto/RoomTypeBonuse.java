package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(name = "room_bonuses")
public class RoomTypeBonuse implements Serializable {
    @TableField(name = "room_type_rt_id", primaryKey = true)
    private int roomTypeId;
    @TableField(name = "bonuses_bo_id", primaryKey = true)
    private int bonuseId;

    private RoomType roomType;
    private Bonuse bonuse;

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getBonuseId() {
        return bonuseId;
    }

    public void setBonuseId(int bonuseId) {
        this.bonuseId = bonuseId;
    }

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
        return getRoomTypeId() == that.getRoomTypeId() &&
                getBonuseId() == that.getBonuseId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomTypeId(), getBonuseId());
    }

    @Override
    public String toString() {
        return "RoomTypeBonuse{" +
                "roomTypeId=" + roomTypeId +
                ", bonuseId=" + bonuseId +
                '}';
    }
}
