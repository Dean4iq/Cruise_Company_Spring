package model.entity;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "excursion")
public class Excursion {
    @TableField(name = "exc_id", primaryKey = true)
    private int id;
    @TableField(name = "info")
    private String information;
    @TableField(name = "price")
    private int price;
    @TableField(name = "harbor_hb_id")
    private int harborId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getHarborId() {
        return harborId;
    }

    public void setHarborId(int harborId) {
        this.harborId = harborId;
    }
}
