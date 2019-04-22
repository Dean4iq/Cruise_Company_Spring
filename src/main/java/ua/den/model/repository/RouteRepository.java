package ua.den.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.den.model.entity.tables.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {

}
