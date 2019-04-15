package model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "excursion")
public class Excursion implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "exc_id")
    private Long id;
    @Column(name = "info")
    private String information;
    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "harbor_hb_id")
    private Harbor harbor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
        return getId().equals(excursion.getId()) &&
                getPrice().equals(excursion.getPrice()) &&
                Objects.equals(getInformation(), excursion.getInformation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInformation(), getPrice());
    }

    @Override
    public String toString() {
        return "Excursion{" +
                "id=" + id +
                ", information='" + information + '\'' +
                ", price=" + price +
                '}';
    }
}
