package model.entity.dto;

import annotation.TableField;
import annotation.TableName;

@TableName(name = "harbor")
public class Harbor {
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
}
