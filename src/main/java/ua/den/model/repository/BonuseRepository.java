package ua.den.model.repository;

import ua.den.model.entity.tables.Bonuse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonuseRepository extends JpaRepository<Bonuse, Long> {
}
