package controller.command;

import exception.NoSuchIdException;
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

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String cruiseId = (String) session.getAttribute("selectedCruiseId");
        String roomId = request.getParameter("roomId");

        if (roomId != null) {
            session.setAttribute("roomId", roomId);
            session.setAttribute("shipRoomNumber", request.getParameter("shipRoomId"));
            return "redirect: /user/cart";
        }

        List<Room> roomList = CabinSelectionService.INSTANCE.getCruiseLoadInfo(cruiseId);

        try {
            Cruise cruise = CabinSelectionService.INSTANCE.getSearchedCruiseInfo(cruiseId);
            roomList.forEach(room -> room.setPrice(room.getRoomType().getPriceModifier() * cruise.getPrice()));
            List<Ticket> tickets = CabinSelectionService.INSTANCE.getTicketsForCruise(cruiseId);

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
}
