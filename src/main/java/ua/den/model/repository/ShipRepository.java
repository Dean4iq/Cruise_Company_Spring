package ua.den.model.repository;

import ua.den.model.entity.dto.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Long> {
}
