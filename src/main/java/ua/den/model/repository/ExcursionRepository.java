package ua.den.model.repository;

import ua.den.model.entity.dto.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcursionRepository extends JpaRepository<Excursion, Long> {
    @Query(value = "SELECT * FROM excursion INNER JOIN harbor ON harbor_hb_id = hb_id INNER JOIN route AS r ON r.harbor_hb_id = hb_id WHERE cruise_cr_id = :cruiseId", nativeQuery = true)
    List<Excursion> getExcursionsForCruise(@Param(value = "cruiseId") Long cruiseId);
}
