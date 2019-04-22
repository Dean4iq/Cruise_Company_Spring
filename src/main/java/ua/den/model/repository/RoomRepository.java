package ua.den.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.den.model.entity.tables.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT * FROM room INNER JOIN ship ON room.ship_sh_id=sh_id INNER JOIN cruise AS cr ON cr.ship_sh_id=sh_id WHERE cr_id = :cruiseId",
            countQuery = "SELECT count(*) FROM room INNER JOIN ship ON room.ship_sh_id=sh_id INNER JOIN cruise AS cr ON cr.ship_sh_id=sh_id WHERE cr_id = :cruiseId",
            nativeQuery = true)
    Page<Room> findByCruise(@Param(value = "cruiseId") Long cruiseId, Pageable pageable);
}
