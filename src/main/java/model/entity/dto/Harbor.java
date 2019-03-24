package model.entity.dto;

import model.annotation.TableField;
import model.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName(name = "harbor")
public class Harbor implements Serializable {
    @TableField(name = "hb_id", primaryKey = true, autoincremented = true)
    private int id;
    @TableField(name = "name")
    private String name;
    @TableField(name = "country_co_id")
    private int countryId;

    private Country country;

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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Harbor harbor = (Harbor) object;
        return getId() == harbor.getId() &&
                getCountryId() == harbor.getCountryId() &&
                Objects.equals(getName(), harbor.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCountryId());
    }

    @Override
    public String toString() {
        return "Harbor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                '}';
    }
}
