package model.repository;

import model.entity.dto.Harbor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarborRepository extends JpaRepository<Harbor, Integer> {
}
