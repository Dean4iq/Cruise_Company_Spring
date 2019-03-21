package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;

@TableName(name = "country")
public class Country implements Serializable {
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
