package model.entity.dto;

import model.annotation.TableField;
import model.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        RoomType roomType = (RoomType) object;
        return getId() == roomType.getId() &&
                getPriceModifier() == roomType.getPriceModifier() &&
                Objects.equals(getName(), roomType.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPriceModifier());
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceModifier=" + priceModifier +
                '}';
    }
}
