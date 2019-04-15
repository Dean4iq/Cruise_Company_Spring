package model.repository;

import model.entity.dto.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(value = "SELECT * FROM ticket WHERE cruise_cr_id = :cruiseId", nativeQuery = true)
    public List<Ticket> findAllByCruiseId(@Param(value = "cruiseId") Integer cruiseId);
}
