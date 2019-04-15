package model.repository;

import model.entity.dto.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT * FROM room INNER JOIN ticket ON room_ro_id=ro_id WHERE cruise_cr_id = :cruiseId", nativeQuery = true)
    public List<Room> findByCruise(@Param(value = "cruiseId") Integer cruiseId);
}
