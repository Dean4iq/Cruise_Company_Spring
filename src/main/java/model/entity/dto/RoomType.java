package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@TableName(name = "room_type")
public class RoomType implements Serializable {
    @TableField(name = "rt_id", primaryKey = true, autoincremented = true)
    private int id;
    @TableField(name = "name")
    private String name;
    @TableField(name = "cost_modifier")
    private int priceModifier;

    private List<Bonuse> bonuses = new ArrayList<>();

    public int getPriceModifier() {
        return priceModifier;
    }

    public void setPriceModifier(int priceModifier) {
        this.priceModifier = priceModifier;
    }

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

    public List<Bonuse> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Bonuse> bonuses) {
        this.bonuses = bonuses;
    }
}
