package model.repository;

import model.entity.dto.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcursionRepository extends JpaRepository<Excursion, Integer> {
    @Query(value = "SELECT * FROM excursion INNER JOIN harbor ON harbor_hb_id = hb_id INNER JOIN route AS r ON r.harbor_id WHERE cruise_cr_id = :cruiseId;", nativeQuery = true)
    public List<Excursion> getExcursionsForCruise(@Param(value = "cruiseId") int cruiseId);
}
