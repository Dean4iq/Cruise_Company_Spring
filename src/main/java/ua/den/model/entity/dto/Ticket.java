package ua.den.model.entity.dto;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ti_id")
    private Long id;
    @Column(name = "purchase_date")
    private Timestamp purchaseDate;
    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "user_login")
    private User user;
    @ManyToOne
    @JoinColumn(name = "room_ro_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "cruise_cr_id")
    private Cruise cruise;

    @ManyToMany
    @JoinTable(name = "ticket_excursion",
            joinColumns = @JoinColumn(name = "ticket_ti_id"),
            inverseJoinColumns = @JoinColumn(name = "excursion_exc_id"))
    private Set<Excursion> excursions;

    public Ticket() {
    }

    public Ticket(Builder builder) {
        this.id = builder.id;
        this.purchaseDate = builder.purchaseDate;
        this.price = builder.price;
        this.user = builder.user;
        this.room = builder.room;
        this.cruise = builder.cruise;
    }

    public static class Builder {
        private Long id;
        private Timestamp purchaseDate;
        private Integer price;
        private User user;
        private Room room;
        private Cruise cruise;

        public Builder id(Long id) {
            this.id = id;

            return this;
        }

        public Builder purchaseDate(Timestamp purchaseDate) {
            this.purchaseDate = purchaseDate;

            return this;
        }

        public Builder price(Integer price) {
            this.price = price;

            return this;
        }

        public Builder user(User user) {
            this.user = user;

            return this;
        }

        public Builder room(Room room) {
            this.room = room;

            return this;
        }

        public Builder cruise(Cruise cruise) {
            this.cruise = cruise;

            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Cruise getCruise() {
        return cruise;
    }

    public void setCruise(Cruise cruise) {
        this.cruise = cruise;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) object;
        return getId().equals(ticket.getId()) &&
                getPrice().equals(ticket.getPrice()) &&
                Objects.equals(getPurchaseDate(), ticket.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPurchaseDate(), getPrice());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", price=" + price +
                '}';
    }
}
