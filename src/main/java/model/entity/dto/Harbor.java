package model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "harbor")
public class Harbor implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "hb_id")
    private Long id;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_co_id")
    private Country country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return getId().equals(harbor.getId()) &&
                Objects.equals(getName(), harbor.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Harbor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
