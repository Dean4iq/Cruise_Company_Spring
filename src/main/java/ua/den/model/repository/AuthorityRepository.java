package ua.den.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.den.model.entity.tables.Authority;
import ua.den.model.entity.tables.AuthorityType;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findOneByRole(AuthorityType role);
}
