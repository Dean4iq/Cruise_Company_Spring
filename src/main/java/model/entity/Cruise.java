package model.entity;

import annotation.TableField;
import annotation.TableName;

import java.sql.Timestamp;

@TableName(name = "cruise")
public class Cruise {
    @TableField(name = "cr_id", primaryKey = true)
    private int id;
    @TableField(name = "name")
    private String name;
    @TableField(name = "price")
    private int price;
    @TableField(name = "date")
    private Timestamp date;
    @TableField(name = "ship_sh_id")
    private int shipId;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getShipId() {
        return shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }
}
