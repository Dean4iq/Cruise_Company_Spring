package model.entity;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "room")
public class Room {
    @TableField(name = "ro_id", primaryKey = true)
    private int id;
    @TableField(name = "room_type_rt_id")
    private int roomTypeId;
    @TableField(name = "ship_sh_id")
    private int shipId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }
}
