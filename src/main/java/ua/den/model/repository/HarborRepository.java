package ua.den.model.repository;

import ua.den.model.entity.tables.Harbor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarborRepository extends JpaRepository<Harbor, Long> {
}
