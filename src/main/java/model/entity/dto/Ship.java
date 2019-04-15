package model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ship")
public class Ship implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "sh_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "crew_number")
    private Integer crewNumber;

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

    public Integer getCrewNumber() {
        return crewNumber;
    }

    public void setCrewNumber(Integer crewNumber) {
        this.crewNumber = crewNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Ship ship = (Ship) object;
        return getId().equals(ship.getId()) &&
                Objects.equals(getName(), ship.getName()) &&
                getCrewNumber().equals(ship.getCrewNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCrewNumber());
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", crewNumber=" + crewNumber +
                '}';
    }
}
