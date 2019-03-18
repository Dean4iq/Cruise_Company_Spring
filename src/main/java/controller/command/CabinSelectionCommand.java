package controller.command;

import model.entity.dto.Room;
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

        List<Room> roomList = CabinSelectionService.INSTANCE.getCruiseLoadInfo(cruiseId);

        request.setAttribute("roomList", roomList);
        return "/WEB-INF/user/tickets.jsp";
    }
}
