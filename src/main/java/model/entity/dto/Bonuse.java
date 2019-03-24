package model.entity.dto;

import model.annotation.TableField;
import model.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(name = "bonuses")
public class Bonuse implements Serializable {
    @TableField(name = "bo_id", primaryKey = true, autoincremented = true)
    private int id;
    @TableField(name = "name")
    private String name;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Bonuse bonuse = (Bonuse) object;
        return getId() == bonuse.getId()
                && Objects.equals(getName(), bonuse.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Bonuse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
