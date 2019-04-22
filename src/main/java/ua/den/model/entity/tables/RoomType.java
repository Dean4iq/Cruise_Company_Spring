package ua.den.model.entity.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room_type")
public class RoomType implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "rt_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "cost_modifier")
    private Integer priceModifier;

    @ManyToMany
    @JoinTable(name = "room_bonuses",
            joinColumns = @JoinColumn(name = "room_type_rt_id"),
            inverseJoinColumns = @JoinColumn(name = "bonuses_bo_id"))
    private List<Bonuse> bonuses = new ArrayList<>();

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

    public Integer getPriceModifier() {
        return priceModifier;
    }

    public void setPriceModifier(Integer priceModifier) {
        this.priceModifier = priceModifier;
    }

    public List<Bonuse> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<Bonuse> bonuses) {
        this.bonuses = bonuses;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        RoomType roomType = (RoomType) object;
        return getId().equals(roomType.getId()) &&
                getPriceModifier().equals(roomType.getPriceModifier()) &&
                Objects.equals(getName(), roomType.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPriceModifier());
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceModifier=" + priceModifier +
                '}';
    }
}
