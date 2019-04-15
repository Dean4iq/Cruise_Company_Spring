package model.repository;

import model.entity.dto.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExcursionRepository extends JpaRepository<Excursion, Integer> {
    //TODO
    @Query(value = "SELECT * FROM excursion WHERE cruiseId = :cruiseId;", nativeQuery = true)
    public List<Excursion> getExcursionsForCruise(@Param(value = "cruiseId") int cruiseId);
}
