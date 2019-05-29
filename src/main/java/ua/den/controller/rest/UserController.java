package ua.den.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ua.den.model.entity.tables.Cruise;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.service.CruiseService;
import ua.den.model.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CruiseService cruiseService;

    @GetMapping("cruises")
    public List<Cruise> getCruisesList() {
        return cruiseService.getAllCruises();
    }

    @GetMapping("cruises/{cruise-id}")
    public Cruise getCruiseById(@PathVariable("cruise-id") Cruise cruise) {
        return cruise;
    }

    @GetMapping("cruises/{cruise-id}/tickets")
    public List<Ticket> getTicketsForCruise(@PathVariable("cruise-id") Cruise cruise) {
        return ticketService.getTicketsForCruise(cruise.getId());
    }

    @GetMapping("cruises/{cruise-id}/tickets/page/{page}")
    public Page<Ticket> getTicketsForCruisePageable(@PathVariable("cruise-id") Cruise cruise,
                                            @PathVariable("page") Integer page) {
        return ticketService.getPaginatedTicketListForCruise(cruise.getId(), page);
    }

    @GetMapping("tickets")
    public List<Ticket> getTicketListForUser(@RequestParam("userName") String userLogin) {
        return ticketService.getTicketsForUser(userLogin);
    }

    @GetMapping("tickets/{ticket-id}")
    public Ticket getTicket(@PathVariable("ticket-id") Ticket ticket) {
        return ticket;
    }

    @PostMapping("tickets")
    public void createTicket(@ModelAttribute("ticket-data") Ticket ticket) {
        ticketService.insertTicketInDB(ticket);
    }
}
