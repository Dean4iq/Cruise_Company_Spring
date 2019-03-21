package controller.command;

import exception.NoResultException;
import exception.NoSuchIdException;
import model.entity.Cart;
import model.entity.dto.Cruise;
import model.entity.dto.Room;
import model.entity.dto.Ticket;
import model.entity.dto.User;
import model.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

public class CartCommand implements Command {
    public static final Logger LOG = LogManager.getLogger(CartCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("userCart");

        if (cart == null) {
            cart = new Cart();

            try {
                Ticket ticket = setTicketData(request);
                cart.setTicket(ticket);

                session.setAttribute("sessionCart", cart);
            } catch (NoResultException e) {
                LOG.warn(e);
            }
        }

        if (request.getParameter("payForTicket") != null) {
            payForTicket(request);
        } else if (request.getParameter("declinePayment") != null) {
            declinePayments(request);
        }

        return "/WEB-INF/user/cart.jsp";
    }

    private Ticket setTicketData(HttpServletRequest request) throws NoResultException {
        HttpSession session = request.getSession();
        String cruiseId = (String) session.getAttribute("selectedCruiseId");
        String roomId = (String) session.getAttribute("roomId");
        String userName = ((User) session.getAttribute("User")).getLogin();

        if (cruiseId == null || roomId == null) {
            throw new NoResultException();
        }

        Cruise cruise = CartService.INSTANCE.getCruiseInfo(cruiseId);
        Room room = CartService.INSTANCE.getRoomInfo(roomId);

        room.setPrice(room.getRoomType().getPriceModifier() * cruise.getPrice());

        return new Ticket.Builder()
                .cruiseId(cruise.getId())
                .roomId(room.getId())
                .purchaseDate(new Timestamp(new Date().getTime()))
                .login(userName)
                .price(cruise.getPrice())
                .room(room)
                .cruise(cruise)
                .build();
    }

    private void payForTicket(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("sessionCart");

        try {
            CartService.INSTANCE.applyTicketPurchasing(cart.getTicket(), cart.getExcursionList());
            session.removeAttribute("sessionCart");
            session.removeAttribute("selectedCruiseId");
            session.removeAttribute("roomId");
        } catch (NoSuchIdException e) {
            LOG.error(e);
        }
    }

    private void declinePayments(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute("sessionCart");
        session.removeAttribute("selectedCruiseId");
        session.removeAttribute("roomId");
    }
}
