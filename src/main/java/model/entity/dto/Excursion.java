package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(name = "excursion")
public class Excursion implements Serializable {
    @TableField(name = "exc_id", primaryKey = true)
    private int id;
    @TableField(name = "info")
    private String information;
    @TableField(name = "price")
    private int price;
    @TableField(name = "harbor_hb_id")
    private int harborId;

    private Harbor harbor;

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

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Excursion excursion = (Excursion) object;
        return getId() == excursion.getId() &&
                getPrice() == excursion.getPrice() &&
                getHarborId() == excursion.getHarborId() &&
                Objects.equals(getInformation(), excursion.getInformation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInformation(), getPrice(), getHarborId());
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", information='" + information + '\'' +
                ", price=" + price +
                ", harborId=" + harborId +
                '}';
    }
}
