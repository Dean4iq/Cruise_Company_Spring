package model.repository;

import model.entity.dto.Bonuse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonuseRepository extends JpaRepository<Bonuse, Integer> {
}
