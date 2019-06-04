package ua.den.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ua.den.model.entity.tables.*;
import ua.den.model.repository.HarborRepository;
import ua.den.model.service.CruiseService;
import ua.den.model.service.RoomService;
import ua.den.model.service.TicketService;
import ua.den.model.service.UserService;
import java.util.List;

//REST controller for ROLE_USER
@RestController
@RequestMapping("user")
public class UserRestController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CruiseService cruiseService;
    @Autowired
    private UserService userService;
    @Autowired
    private HarborRepository harborRepository;
    @Autowired
    private RoomService roomService;

    @GetMapping(value = "search")
    public List<Harbor> getSearchPage() {
        return harborRepository.findAll();
    }

    @GetMapping(value = "search-commit")
    public List<Cruise> getSearchInfo(@RequestParam("country_id-en-route") Country country) {
        return cruiseService.searchCruiseByCountry(country.getName());
    }

    @GetMapping("cruises/{cruise-id}")
    public Cruise getCruiseById(@PathVariable("cruise-id") Cruise cruise) {
        return cruise;
    }

    @GetMapping("cruises/{cruise-id}/places")
    public Page<Room> getTicketsForCruisePageable(@PathVariable("cruise-id") Cruise cruise,
                                                  @Nullable @RequestParam("page") Integer page) {
        page = (page == null || page == 0) ? 1 : page;

        return roomService.getRoomListByCruise(cruise.getId(), page);
    }

    @GetMapping("tickets")
    public List<Ticket> getTicketListForUser(@RequestParam("userName") String userLogin) {
        return ticketService.getTicketsForUser(userLogin);
    }

    @GetMapping("tickets/{ticket-id}")
    public Ticket getTicket(@PathVariable("ticket-id") Ticket ticket) {
        return ticket;
    }

    @GetMapping("profile")
    public User getUserData(@RequestParam("login") User user) {
        return user;
    }

    @PostMapping("tickets")
    public void createTicket(@RequestBody Ticket ticket) {
        ticketService.insertTicketInDB(ticket);
    }

    @PostMapping("cruises")
    public void createCruise(@RequestBody Cruise cruise) {
        cruiseService.createCruise(cruise);
    }

    @PutMapping("cruises/{cruise-id}")
    public void updateCruise(@RequestBody Cruise cruise) {
        cruiseService.updateCruise(cruise);
    }

    @PutMapping("cruises/{cruise-id}/places/{id}")
    public void updatePlaceData(@RequestBody Room room) {
        roomService.updateRoom(room);
    }

    @PutMapping("profile")
    public void updateProfileData(@RequestBody User user) {
        userService.updateUserData(user);
    }
}
