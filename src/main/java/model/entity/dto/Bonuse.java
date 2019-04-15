package model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="bonuses")
public class Bonuse implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "bo_id")
    private Long id;
    @Column(name = "name")
    private String name;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Bonuse bonuse = (Bonuse) object;
        return getId().equals(bonuse.getId())
                && Objects.equals(getName(), bonuse.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return "Bonuse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
