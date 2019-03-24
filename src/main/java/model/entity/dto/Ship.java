package model.entity.dto;

import model.annotation.TableField;
import model.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(name = "ship")
public class Ship implements Serializable {
    @TableField(name = "sh_id", primaryKey = true)
    private int id;
    @TableField(name = "name")
    private String name;
    @TableField(name = "crew_number")
    private int crewNumber;

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

    public int getCrewNumber() {
        return crewNumber;
    }

    public void setCrewNumber(int crewNumber) {
        this.crewNumber = crewNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Ship ship = (Ship) object;
        return getId() == ship.getId() &&
                Objects.equals(getName(), ship.getName()) &&
                getCrewNumber() == ship.getCrewNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCrewNumber());
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", crewNumber=" + crewNumber +
                '}';
    }
}
