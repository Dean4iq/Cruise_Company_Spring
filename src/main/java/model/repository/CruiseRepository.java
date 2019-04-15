package model.repository;

import model.entity.dto.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CruiseRepository extends JpaRepository<Cruise, Integer> {
}
