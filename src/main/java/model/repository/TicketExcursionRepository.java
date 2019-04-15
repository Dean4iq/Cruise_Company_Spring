package model.repository;

import model.entity.dto.TicketExcursion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketExcursionRepository extends JpaRepository<TicketExcursion, Integer> {
}
