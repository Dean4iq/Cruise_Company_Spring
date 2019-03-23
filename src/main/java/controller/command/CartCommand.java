package controller.command;

import exception.NoResultException;
import exception.NoSuchIdException;
import model.entity.dto.Cart;
import model.entity.dto.*;
import model.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.PropertiesSource;
import util.ResourceBundleGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

public class CartCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(CartCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("sessionCart");

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
            request.setAttribute("paymentAccepted", true);
            return "/WEB-INF/user/cart.jsp";
        } else if (request.getParameter("declinePayment") != null) {
            declinePayments(request);
            request.setAttribute("paymentDeclined", true);
            return "/WEB-INF/user/cart.jsp";
        } else if (request.getParameter("removeExcursion") != null) {
            removeExcursionFromCart(request);
            return "redirect: /user/cart";
        } else if (request.getParameter("addNewExcursion") != null) {
            addExcursionToCart(request);
            return "redirect: /user/cart";
        }

        setExcursionList(request);
        setHarborMap(request);
        setCountryMap(request);
        setExcursionInfoMap(request);

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

    private void setExcursionList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String cruiseId = (String) session.getAttribute("selectedCruiseId");
        Cart cart = (Cart) session.getAttribute("sessionCart");

        if (cruiseId != null && cart != null) {
            List<Excursion> excursionList = CartService.INSTANCE.getExcursionList(cruiseId).stream()
                    .filter(excursion -> cart.getExcursionList().stream().noneMatch(excursionInCart ->
                            excursion.getId() == excursionInCart.getId())).collect(Collectors.toList());
            request.setAttribute("excursionList", excursionList);
        }
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

    private void removeExcursionFromCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("sessionCart");

        String excursionId = request.getParameter("excursionId");

        try {
            Excursion excursionToDelete = CartService.INSTANCE.getExcursionById(Integer.parseInt(excursionId));
            cart.getTicket().setPrice(cart.getTicket().getPrice() - excursionToDelete.getPrice());
        } catch (NoSuchIdException e) {
            LOG.error(e);
        }

        List<Excursion> excursionList = cart.getExcursionList().stream().filter(excursion ->
                excursion.getId() != Integer.parseInt(excursionId)).collect(Collectors.toList());

        cart.setExcursionList(excursionList);
    }

    private void addExcursionToCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("sessionCart");
        String excursionId = request.getParameter("excursionId");
        String cruiseId = (String) request.getSession().getAttribute("selectedCruiseId");

        List<Excursion> excursionList = CartService.INSTANCE.getExcursionList(cruiseId);

        Excursion excursionToAdd = excursionList.stream().filter(excursion ->
                excursion.getId() == Integer.parseInt(excursionId)).findFirst().orElse(new Excursion());

        cart.getTicket().setPrice(cart.getTicket().getPrice() + excursionToAdd.getPrice());
        cart.getExcursionList().add(excursionToAdd);
    }

    private void setCountryMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute("sessionLanguage");

        Map<String, String> countryMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.COUNTRY.source, sessionLanguage);

        countryMap = countryMap.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        request.setAttribute("countryMap", countryMap);
    }

    private void setHarborMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute("sessionLanguage");

        Map<String, String> harborMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.HARBOR.source, sessionLanguage);

        request.setAttribute("harborMap", harborMap);
    }

    private void setExcursionInfoMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute("sessionLanguage");

        Map<String, String> excursionInfoMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.EXCURSION_INFO.source,
                        sessionLanguage);

        request.setAttribute("localeTourInfo", excursionInfoMap);
    }
}
