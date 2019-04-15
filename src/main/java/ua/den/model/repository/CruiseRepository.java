package ua.den.model.repository;

import ua.den.model.entity.dto.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CruiseRepository extends JpaRepository<Cruise, Integer> {
}
