package ua.den.model.repository;

import ua.den.model.entity.dto.Harbor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarborRepository extends JpaRepository<Harbor, Integer> {
}
