package ua.den.controller.command;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ua.den.model.entity.tables.Cart;
import ua.den.model.exception.NoSuchIdException;
import ua.den.model.entity.tables.Cruise;
import ua.den.model.entity.tables.Room;
import ua.den.model.entity.tables.Ticket;
import ua.den.model.service.CabinSelectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class {@code CabinSelectionCommand} provide methods to select rooms on ship and checks their availability
 * for purchasing. Also class uses page with pagination
 *
 * @author Dean4iq
 * @version 1.0
 */
@Component
@SessionScope
public class CabinSelectionCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(CabinSelectionCommand.class);
    private static final String USER_TICKET_PAGE_JSP = "user/tickets";
    private static final String USER_CART_REDIRECT = "redirect:/user/cart";

    private final int ELEMENTS_ON_PAGE = 10;

    @Autowired
    private CabinSelectionService cabinSelectionService;

    /**
     * Returns link to the selection page and provides data about rooms on ship
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the selection page
     */
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String roomId = request.getParameter("roomId");

        if (session.getAttribute("sessionCart") != null) {
            return USER_CART_REDIRECT;
        }

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
        String cruiseId = (String) session.getAttribute("selectedCruiseId");

        List<Room> roomList = setUpPages(request,cruiseId);
        setUpRoomList(request, roomList, cruiseId);

        return roomList;
    }

    /**
     * Checks rooms availability in DB and other user sessions
     *
     * @param request  stores and provides user data to process and link to session and context
     * @param roomList list of rooms to operate
     * @param cruiseId id of cruise to differ rooms in list
     * @throws NoSuchIdException if there will be no tickets for cruise
     */
    private void setUpRoomList(HttpServletRequest request, List<Room> roomList, String cruiseId) {
        Set<HttpSession> sessions = (HashSet<HttpSession>) request.getServletContext()
                .getAttribute("userSession");
        Cruise cruise = cabinSelectionService.getSearchedCruiseInfo(cruiseId);
        roomList.forEach(room -> room.setPrice(room.getRoomType().getPriceModifier() * cruise.getPrice()));
        List<Ticket> tickets = cabinSelectionService.getTicketsForCruise(cruiseId);

        sessions.forEach(session -> {
            Cart cart = (Cart) session.getAttribute("sessionCart");
            if (cart != null && cart.getTicket() != null) {
                roomList.forEach(room -> {
                    if (room.getId().equals(cart.getTicket().getRoom().getId())
                            && Integer.parseInt(cruiseId) == cart.getTicket().getCruise().getId()) {
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

    /**
     * Calculates and records page numbers to jsp page and returns list of rooms for selected page
     *
     * @param request  stores and provides user data to process and link to session and context
     * @param cruiseId id of cruise to get rooms from DB
     * @return list of rooms on ship for the selection page
     */
    private List<Room> setUpPages(HttpServletRequest request, String cruiseId) {
        String pageNumber = request.getParameter("page");
        int page = (pageNumber != null && !pageNumber.equals("")) ? Integer.parseInt(pageNumber) : 0;

        Page<Room> roomList = cabinSelectionService.getCruiseLoadInfo(cruiseId, page, ELEMENTS_ON_PAGE);

        request.setAttribute("currentPage", page);

        request.setAttribute("pageNumber", getPageNumber(roomList));
        request.setAttribute("countModifier", page * ELEMENTS_ON_PAGE);

        return roomList.getContent();
    }

    /**
     * Returns number of pages depends on number of rooms
     *
     * @param roomList list of rooms to process
     * @return total number of pages
     */
    private long getPageNumber(Page<Room> roomList) {
        return (long) Math.ceil((double) roomList.getTotalElements() / ELEMENTS_ON_PAGE);
    }
}
