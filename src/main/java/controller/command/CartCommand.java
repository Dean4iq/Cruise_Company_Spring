package controller.command;

import model.exception.NoResultException;
import model.exception.NoSuchIdException;
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

/**
 * Class {@code CartCommand} provide methods to operate with the session cart for users
 *
 * @author Dean4iq
 * @version 1.0
 */
public class CartCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(CartCommand.class);

    private static final String CART_LINK = "/WEB-INF/user/cart.jsp";
    private static final String SESSION_CART = "sessionCart";
    private static final String SELECTED_CRUISE_ID = "selectedCruiseId";
    private static final String ROOM_ID = "roomId";
    private static final String SESSION_LANGUAGE = "sessionLanguage";

    private CartService cartService = CartService.getInstance();

    /**
     * Calls methods to operate with session cart and returns link to the cart page
     *
     * @param request stores and provides user data to process and link to session and context
     * @return link to the cart page
     */
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute(SESSION_CART);

        if (cart == null) {
            cart = new Cart();

            try {
                Ticket ticket = setTicketData(request);
                cart.setTicket(ticket);

                session.setAttribute(SESSION_CART, cart);
            } catch (NoResultException e) {
                LOG.warn(e);
            }
        }

        if (request.getParameter("payForTicket") != null) {
            payForTicket(request);
            request.setAttribute("paymentAccepted", true);
            return CART_LINK;
        } else if (request.getParameter("declinePayment") != null) {
            declinePayments(request);
            request.setAttribute("paymentDeclined", true);
            return CART_LINK;
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

        return CART_LINK;
    }

    /**
     * Initializes ticket data depending on the choice of the user in previous pages
     *
     * @param request stores and provides user data to process and link to session and context
     * @return ticket with filled data
     * @throws NoResultException if there will be no ticket for these data
     */
    private Ticket setTicketData(HttpServletRequest request) throws NoResultException {
        HttpSession session = request.getSession();
        String cruiseId = (String) session.getAttribute(SELECTED_CRUISE_ID);
        String roomId = (String) session.getAttribute(ROOM_ID);
        String userName = ((User) session.getAttribute("User")).getLogin();

        if (cruiseId == null || roomId == null) {
            throw new NoResultException();
        }

        Cruise cruise = cartService.getCruiseInfo(cruiseId);
        Room room = cartService.getRoomInfo(roomId);

        room.setPrice(room.getRoomType().getPriceModifier() * cruise.getPrice());

        return new Ticket.Builder()
                .cruiseId(cruise.getId())
                .roomId(room.getId())
                .purchaseDate(new Timestamp(new Date().getTime()))
                .login(userName)
                .price(room.getPrice())
                .room(room)
                .cruise(cruise)
                .build();
    }

    /**
     * Gets and adds to request attributes list of excursions, available for selected cruise
     * in harbors on route
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void setExcursionList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String cruiseId = (String) session.getAttribute(SELECTED_CRUISE_ID);
        Cart cart = (Cart) session.getAttribute(SESSION_CART);

        if (cruiseId != null && cart != null) {
            List<Excursion> excursionList = cartService.getExcursionList(cruiseId).stream()
                    .filter(excursion -> cart.getExcursionList().stream().noneMatch(excursionInCart ->
                            excursion.getId() == excursionInCart.getId())).collect(Collectors.toList());
            request.setAttribute("excursionList", excursionList);
        }
    }

    /**
     * Process the request to apply the ticket purchasing
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void payForTicket(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute(SESSION_CART);

        try {
            cartService.applyTicketPurchasing(cart.getTicket(), cart.getExcursionList());
            session.removeAttribute(SESSION_CART);
            session.removeAttribute(SELECTED_CRUISE_ID);
            session.removeAttribute(ROOM_ID);
        } catch (NoSuchIdException e) {
            LOG.error(e);
        }
    }

    /**
     * Cleans the cart and session from purchasing data
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void declinePayments(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute(SESSION_CART);
        session.removeAttribute(SELECTED_CRUISE_ID);
        session.removeAttribute(ROOM_ID);
    }

    /**
     * Method to remove excursions from the cart on user demand
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void removeExcursionFromCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(SESSION_CART);

        String excursionId = request.getParameter("excursionId");

        try {
            Excursion excursionToDelete = cartService.getExcursionById(Integer.parseInt(excursionId));
            cart.getTicket().setPrice(cart.getTicket().getPrice() - excursionToDelete.getPrice());
        } catch (NoSuchIdException e) {
            LOG.error(e);
        }

        List<Excursion> excursionList = cart.getExcursionList().stream().filter(excursion ->
                excursion.getId() != Integer.parseInt(excursionId)).collect(Collectors.toList());

        cart.setExcursionList(excursionList);
    }

    /**
     * Method to add excursions to the cart on user demand
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void addExcursionToCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(SESSION_CART);
        String excursionId = request.getParameter("excursionId");
        String cruiseId = (String) request.getSession().getAttribute(SELECTED_CRUISE_ID);

        List<Excursion> excursionList = cartService.getExcursionList(cruiseId);

        Excursion excursionToAdd = excursionList.stream().filter(excursion ->
                excursion.getId() == Integer.parseInt(excursionId)).findFirst().orElse(new Excursion());

        cart.getTicket().setPrice(cart.getTicket().getPrice() + excursionToAdd.getPrice());
        cart.getExcursionList().add(excursionToAdd);
    }

    /**
     * Method to add locale values of country names
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void setCountryMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute(SESSION_LANGUAGE);

        Map<String, String> countryMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.COUNTRY.source, sessionLanguage);

        countryMap = countryMap.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        request.setAttribute("countryMap", countryMap);
    }

    /**
     * Method to add locale values of harbor names
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void setHarborMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute(SESSION_LANGUAGE);

        Map<String, String> harborMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.HARBOR.source, sessionLanguage);

        request.setAttribute("harborMap", harborMap);
    }

    /**
     * Method to add locale values of excursion descriptions
     *
     * @param request stores and provides user data to process and link to session and context
     */
    private void setExcursionInfoMap(HttpServletRequest request) {
        String sessionLanguage = (String) request.getSession().getAttribute(SESSION_LANGUAGE);

        Map<String, String> excursionInfoMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.EXCURSION_INFO.source,
                        sessionLanguage);

        request.setAttribute("localeTourInfo", excursionInfoMap);
    }
}
