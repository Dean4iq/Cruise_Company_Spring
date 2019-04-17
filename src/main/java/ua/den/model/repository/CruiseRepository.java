package ua.den.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.den.model.entity.dto.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {
    @Query(value = "SELECT * FROM cruise INNER JOIN route ON cruise_cr_id=cr_id INNER JOIN harbor ON harbor_hb_id=hb_id INNER JOIN country AS c ON country_co_id = co_id WHERE c.name = :countryName GROUP BY cruise.name;",
            nativeQuery = true)
    List<Cruise> getCruiseByCountry(@Param(value = "countryName") String countryName);
}
