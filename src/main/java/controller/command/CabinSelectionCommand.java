package controller.command;

import controller.util.Pagination;
import model.exception.NoSuchIdException;
import model.entity.dto.Cruise;
import model.entity.dto.Room;
import model.entity.dto.Ticket;
import model.service.CabinSelectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CabinSelectionCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(CabinSelectionCommand.class);
    private CabinSelectionService cabinSelectionService = CabinSelectionService.getInstance();

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String cruiseId = (String) session.getAttribute("selectedCruiseId");
        String roomId = request.getParameter("roomId");

        if (session.getAttribute("sessionCart") != null) {
            return "redirect: /user/cart";
        }

        if (roomId != null) {
            session.setAttribute("roomId", roomId);
            session.setAttribute("shipRoomNumber", request.getParameter("shipRoomId"));
            return "redirect: /user/cart";
        }

        List<Room> roomList = setUpPages(request, cabinSelectionService.getCruiseLoadInfo(cruiseId));

        try {
            Cruise cruise = cabinSelectionService.getSearchedCruiseInfo(cruiseId);
            roomList.forEach(room -> room.setPrice(room.getRoomType().getPriceModifier() * cruise.getPrice()));
            List<Ticket> tickets = cabinSelectionService.getTicketsForCruise(cruiseId);

            tickets.forEach(ticket ->
                    roomList.forEach(room -> {
                        if (room.getId() == ticket.getRoomId()) {
                            room.setAvailable(false);
                        }
                    }));
        } catch (NoSuchIdException e) {
            LOG.error(e);
            return new ExceptionCommand().execute(request);
        }

        request.setAttribute("roomList", roomList);

        return "/WEB-INF/user/tickets.jsp";
    }

    private List<Room> setUpPages(HttpServletRequest request, List<Room> roomList) {
        Pagination<Room> pagination = new Pagination<>();
        String pageNumber = request.getParameter("page");

        int page = (pageNumber != null && !pageNumber.equals("")) ? Integer.parseInt(pageNumber) : 1;
        request.setAttribute("currentPage", page);

        request.setAttribute("pageNumber", getPageNumber(roomList));
        request.setAttribute("countModifier", pagination.getPageCountModifier(page));

        return pagination.getPageList(roomList, page);
    }

    private int getPageNumber(List<Room> roomList) {
        Pagination<Room> pagination = new Pagination<>();
        return pagination.getPageNumber(roomList);
    }
}
