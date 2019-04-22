package ua.den.model.repository;

import ua.den.model.entity.tables.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT * FROM ticket WHERE cruise_cr_id = :cruiseId", nativeQuery = true)
    public List<Ticket> findAllByCruiseId(@Param(value = "cruiseId") Long cruiseId);
}
