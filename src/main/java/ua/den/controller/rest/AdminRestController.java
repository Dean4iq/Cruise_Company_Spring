package ua.den.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.service.TicketService;

import java.util.List;

//REST controller for ROLE_ADMIN
@RestController
@RequestMapping("admin")
public class AdminRestController {
    @Autowired
    private TicketService ticketService;

    @GetMapping(value = "tickets")
    @ResponseBody
    private List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping(value = "/tickets/{id}")
    @ResponseBody
    private Ticket getTicket(@PathVariable("id") Ticket ticket) {
        return ticket;
    }
}
