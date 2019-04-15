package model.repository;

import model.entity.dto.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Integer> {
}
