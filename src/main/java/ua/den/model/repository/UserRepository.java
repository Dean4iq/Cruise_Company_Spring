package ua.den.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.den.model.entity.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT * FROM user WHERE login = BINARY :login", nativeQuery = true)
    User getUserByLogin(@Param("login") String login);
}