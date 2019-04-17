package ua.den.model.repository;

import ua.den.model.entity.dto.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
