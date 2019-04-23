package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.Room;
import ua.den.model.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Room getRoomById(Long roomId) {
        return roomRepository.getOne(roomId);
    }

    public Page<Room> getCruiseLoadInfo(Long cruiseId, int page, int elementsOnPage) {
        Pageable pageable = PageRequest.of(page, elementsOnPage);

        return roomRepository.findByCruise(cruiseId, pageable);
    }
}