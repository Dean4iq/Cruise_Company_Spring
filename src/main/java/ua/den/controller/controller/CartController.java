package ua.den.controller.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.den.model.entity.tables.*;
import ua.den.model.exception.AlreadyReservedException;
import ua.den.model.exception.NoResultException;
import ua.den.model.service.*;
import ua.den.util.PropertiesSource;
import ua.den.util.ResourceBundleGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Controller
@RequestMapping("/user/cart")
public class CartController {
    private static final Logger LOG = LogManager.getLogger(CartController.class);

    private static final String CART_LINK = "user/cart";
    private static final String CART_LINK_REDIRECT = "redirect:/user/cart";
    private static final String SESSION_CART = "sessionCart";
    private static final String SELECTED_CRUISE_ID = "selectedCruiseId";
    private static final String ROOM_ID = "roomId";

    @Autowired
    private CruiseService cruiseService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private ExcursionService excursionService;
    @Autowired
    private TicketService ticketService;

    @GetMapping("")
    public String getPage(HttpServletRequest request) {
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
            } catch (AlreadyReservedException e) {
                request.setAttribute("alreadyTaken", true);
                declinePayments(request);
                return CART_LINK;
            }
        }

        setExcursionList(request);
        setHarborMap(request);
        setCountryMap(request);
        setExcursionInfoMap(request);

        return CART_LINK;
    }

    @PostMapping("/add-excursion")
    public String addExcursion(@RequestParam("excursionId") Long excursionId,
                               HttpServletRequest request) {
        addExcursionToCart(request, excursionId);
        return CART_LINK_REDIRECT;
    }

    @PostMapping("/remove-excursion")
    public String removeExcursion(@RequestParam("excursionId") Long excursionId,
                                  HttpServletRequest request) {
        removeExcursionFromCart(request, excursionId);
        return CART_LINK_REDIRECT;
    }

    @PostMapping("/accept-payment")
    public String acceptingPurchasing(HttpServletRequest request) {
        payForTicket(request);
        request.setAttribute("paymentAccepted", true);
        return CART_LINK;
    }

    @PostMapping("/decline-payment")
    public String decliningPurchasing(HttpServletRequest request) {
        declinePayments(request);
        request.setAttribute("paymentDeclined", true);
        return CART_LINK;
    }

    private Ticket setTicketData(HttpServletRequest request)
            throws NoResultException, AlreadyReservedException {
        HttpSession session = request.getSession();
        Long cruiseId = (Long) session.getAttribute(SELECTED_CRUISE_ID);
        Long roomId = (Long) session.getAttribute(ROOM_ID);

        if (cruiseId == null || roomId == null) {
            throw new NoResultException();
        }
        checkTicketAvailability(request, roomId, cruiseId);

        Cruise cruise = cruiseService.getCruiseById(cruiseId);
        Room room = roomService.getRoomById(roomId);

        room.setPrice(room.getRoomType().getPriceModifier() * cruise.getPrice());

        return new Ticket.Builder()
                .purchaseDate(new Timestamp(new Date().getTime()))
                .price(room.getPrice())
                .user((User) session.getAttribute("User"))
                .room(room)
                .cruise(cruise)
                .build();
    }

    private void checkTicketAvailability(HttpServletRequest request, Long roomId, Long cruiseId)
            throws AlreadyReservedException {
        Set<HttpSession> sessions = (HashSet<HttpSession>) request.getServletContext()
                .getAttribute("userSession");

        if (!(checkAvailabilityInCart(sessions, roomId, cruiseId)
                && checkAvailabilityInDB(roomId, cruiseId))) {
            throw new AlreadyReservedException(roomId);
        }
    }

    private boolean checkAvailabilityInCart(Set<HttpSession> sessions, Long roomNumber, Long cruiseId) {
        return sessions.stream().noneMatch(session -> {
            Cart cart = (Cart) session.getAttribute(SESSION_CART);
            if (cart != null && cart.getTicket() != null) {
                return roomNumber.equals(cart.getTicket().getRoom().getId())
                        && cruiseId.equals(cart.getTicket().getCruise().getId());
            }
            return false;
        });
    }

    private boolean checkAvailabilityInDB(Long roomNumber, Long cruiseId) {
        List<Ticket> tickets = ticketService.getTicketsForCruise(cruiseId);

        return tickets.stream().noneMatch(ticket ->
                roomNumber.equals(ticket.getRoom().getId()));
    }

    private void setExcursionList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long cruiseId = (Long) session.getAttribute(SELECTED_CRUISE_ID);
        Cart cart = (Cart) session.getAttribute(SESSION_CART);

        if (cruiseId != null && cart != null) {
            List<Excursion> excursionList = excursionService.getExcursionForCruise(cruiseId).stream()
                    .filter(excursion -> cart.getExcursionList().stream().noneMatch(excursionInCart ->
                            excursion.getId().equals(excursionInCart.getId()))).collect(Collectors.toList());
            request.setAttribute("excursionList", excursionList);
        }
    }

    private void payForTicket(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute(SESSION_CART);

        Ticket ticket = cart.getTicket();
        ticket.setExcursions(new HashSet<>(cart.getExcursionList()));

        ticketService.insertTicketInDB(cart.getTicket());
        session.removeAttribute(SESSION_CART);
        session.removeAttribute(SELECTED_CRUISE_ID);
        session.removeAttribute(ROOM_ID);

    }

    private void declinePayments(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.removeAttribute(SESSION_CART);
        session.removeAttribute(SELECTED_CRUISE_ID);
        session.removeAttribute(ROOM_ID);
    }

    private void removeExcursionFromCart(HttpServletRequest request, Long excursionId) {
        Cart cart = (Cart) request.getSession().getAttribute(SESSION_CART);

        Excursion excursionToDelete = excursionService.getExcursionById(excursionId);
        cart.getTicket().setPrice(cart.getTicket().getPrice() - excursionToDelete.getPrice());

        List<Excursion> excursionList = cart.getExcursionList().stream().filter(excursion ->
                !excursion.getId().equals(excursionId)).collect(Collectors.toList());

        cart.setExcursionList(excursionList);
    }

    private void addExcursionToCart(HttpServletRequest request, Long excursionId) {
        Cart cart = (Cart) request.getSession().getAttribute(SESSION_CART);
        Long cruiseId = (Long) request.getSession().getAttribute(SELECTED_CRUISE_ID);

        List<Excursion> excursionList = excursionService.getExcursionForCruise(cruiseId);

        Excursion excursionToAdd = excursionList.stream().filter(excursion ->
                excursion.getId().equals(excursionId)).findFirst().orElse(new Excursion());

        cart.getTicket().setPrice(cart.getTicket().getPrice() + excursionToAdd.getPrice());
        cart.getExcursionList().add(excursionToAdd);
    }

    private void setCountryMap(HttpServletRequest request) {
        Locale sessionLocale = LocaleContextHolder.getLocale();

        Map<String, String> countryMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.COUNTRY.source, sessionLocale);

        countryMap = countryMap.entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                        LinkedHashMap::new));

        request.setAttribute("countryMap", countryMap);
    }

    private void setHarborMap(HttpServletRequest request) {
        Locale sessionLocale = LocaleContextHolder.getLocale();

        Map<String, String> harborMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.HARBOR.source, sessionLocale);

        request.setAttribute("harborMap", harborMap);
    }

    private void setExcursionInfoMap(HttpServletRequest request) {
        Locale sessionLocale = LocaleContextHolder.getLocale();

        Map<String, String> excursionInfoMap =
                ResourceBundleGetter.INSTANCE.getResourceMap(PropertiesSource.EXCURSION_INFO.source,
                        sessionLocale);

        request.setAttribute("localeTourInfo", excursionInfoMap);
    }
}
