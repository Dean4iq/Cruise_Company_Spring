package model.entity;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "country")
public class Country {
    @TableField(name = "co_id", primaryKey = true)
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
