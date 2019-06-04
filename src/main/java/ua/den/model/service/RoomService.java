package ua.den.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.den.model.entity.tables.Room;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.repository.RoomRepository;
import ua.den.model.repository.TicketRepository;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public Room getRoomById(Long roomId) {
        return roomRepository.getOne(roomId);
    }

    public Page<Room> getRoomListByCruise(Long cruiseId, int page) {
        Page<Room> roomsInDB = roomRepository.findByCruise(cruiseId, PageRequest.of(page - 1, 10));

        List<Ticket> soldTicketsForCruise = ticketRepository.findAllByCruiseId(cruiseId);

        roomsInDB.get()
                .filter(room -> soldTicketsForCruise.stream().anyMatch(ticket -> room.getId().equals(ticket.getRoom().getId())))
                .forEach(room -> room.setAvailable(false));

        return roomsInDB;
    }

    public Page<Room> getCruiseLoadInfo(Long cruiseId, int page, int elementsOnPage) {
        Pageable pageable = PageRequest.of(page, elementsOnPage);

        return roomRepository.findByCruise(cruiseId, pageable);
    }

    public Room updateRoom(Room room) {
        return roomRepository.save(room);
    }
}