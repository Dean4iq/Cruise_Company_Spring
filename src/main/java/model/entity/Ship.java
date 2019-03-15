package model.entity;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "ship")
public class Ship {
    @TableField(name = "sh_id", primaryKey = true)
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
}
