package model.entity;

import annotation.TableField;
import annotation.TableName;

import java.sql.Timestamp;

@TableName(name = "ticket")
public class Ticket {
    @TableField(name = "ti_id", primaryKey = true)
    private int id;
    @TableField(name = "purchase_date")
    private Timestamp purchaseDate;
    @TableField(name = "price")
    private int price;
    @TableField(name = "user_login")
    private String login;
    @TableField(name = "room_ro_id")
    private int roomId;
    @TableField(name = "cruise_cr_id")
    private int cruiseId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCruiseId() {
        return cruiseId;
    }

    public void setCruiseId(int cruiseId) {
        this.cruiseId = cruiseId;
    }
}
