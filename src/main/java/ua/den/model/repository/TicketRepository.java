package ua.den.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.den.model.entity.tables.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "SELECT * FROM ticket WHERE cruise_cr_id = :cruiseId", nativeQuery = true)
    List<Ticket> findAllByCruiseId(@Param(value = "cruiseId") Long cruiseId);

    List<Ticket> findByUserLogin(String login);
    Page<Ticket> findByCruiseId(Long cruiseId, Pageable pageRequest);
}
