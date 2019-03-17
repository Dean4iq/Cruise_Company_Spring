package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "room_bonuses")
public class RoomTypeBonuse {
    @TableField(name = "room_type_rt_id", primaryKey = true)
    private RoomType roomType;
    @TableField(name = "bonuses_bo_id", primaryKey = true)
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
}
