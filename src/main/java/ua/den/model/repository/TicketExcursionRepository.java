package ua.den.model.repository;

import ua.den.model.entity.tables.TicketExcursion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketExcursionRepository extends JpaRepository<TicketExcursion, Long> {
}
