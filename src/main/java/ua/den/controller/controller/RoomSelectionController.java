package ua.den.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.den.model.entity.tables.Cart;
import ua.den.model.entity.tables.Cruise;
import ua.den.model.entity.tables.Room;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.service.CruiseService;
import ua.den.model.service.RoomService;
import ua.den.model.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("user/tickets")
public class RoomSelectionController {
    private static final String USER_TICKET_PAGE_JSP = "user/tickets";
    private static final String USER_CART_REDIRECT = "redirect:/user/cart";

    private static final int ELEMENTS_ON_PAGE = 10;

    @Autowired
    private CruiseService cruiseService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("")
    public String getPage(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("sessionCart") != null) {
            return USER_CART_REDIRECT;
        }

        return USER_TICKET_PAGE_JSP;
    }

    @PostMapping("")
    public String processRequest(@Param("roomId") Long roomId,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (roomId != null) {
            session.setAttribute("roomId", roomId);
            session.setAttribute("shipRoomNumber", request.getParameter("shipRoomId"));
            return USER_CART_REDIRECT;
        }

        List<Room> roomList = setUpList(request);

        request.setAttribute("roomList", roomList);

        return USER_TICKET_PAGE_JSP;
    }

    private List<Room> setUpList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long cruiseId = Long.parseLong((String) session.getAttribute("selectedCruiseId"));

        List<Room> roomList = setUpPages(request, cruiseId);
        setUpRoomList(request, roomList, cruiseId);

        return roomList;
    }

    private void setUpRoomList(HttpServletRequest request, List<Room> roomList, Long cruiseId) {
        Set<HttpSession> sessions = (HashSet<HttpSession>) request.getServletContext()
                .getAttribute("userSession");

        Cruise cruise = cruiseService.getCruiseById(cruiseId);
        roomList.forEach(room -> room.setPrice(room.getRoomType().getPriceModifier() * cruise.getPrice()));
        List<Ticket> tickets = ticketService.getTicketsForCruise(cruiseId);

        sessions.forEach(session -> {
            Cart cart = (Cart) session.getAttribute("sessionCart");
            if (cart != null && cart.getTicket() != null) {
                roomList.forEach(room -> {
                    if (room.getId().equals(cart.getTicket().getRoom().getId())
                            && cruiseId.equals(cart.getTicket().getCruise().getId())) {
                        room.setAvailable(false);
                    }
                });
            }
        });

        tickets.forEach(ticket ->
                roomList.forEach(room -> {
                    if (room.getId().equals(ticket.getRoom().getId())) {
                        room.setAvailable(false);
                    }
                }));
    }

    private List<Room> setUpPages(HttpServletRequest request, Long cruiseId) {
        String pageNumber = request.getParameter("page");
        int page = (pageNumber != null && !pageNumber.equals("")) ? Integer.parseInt(pageNumber) : 0;

        Page<Room> roomList = roomService.getCruiseLoadInfo(cruiseId, page, ELEMENTS_ON_PAGE);

        request.setAttribute("currentPage", page);

        request.setAttribute("pageNumber", getPageNumber(roomList));
        request.setAttribute("countModifier", page * ELEMENTS_ON_PAGE);

        return roomList.getContent();
    }

    private long getPageNumber(Page<Room> roomList) {
        return (long) Math.ceil((double) roomList.getTotalElements() / ELEMENTS_ON_PAGE);
    }
}
