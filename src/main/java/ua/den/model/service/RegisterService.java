package ua.den.model.service;

import org.springframework.web.context.annotation.SessionScope;
import ua.den.model.exception.NotUniqueLoginException;
import ua.den.model.entity.tables.User;
import ua.den.model.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class {@code RegisterService} provides methods to receive and create data from/to DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
@Service
@SessionScope
public class RegisterService {
    private static final Logger LOG = LogManager.getLogger(RegisterService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Checks if user with defined login not exists in the system
     *
     * @param login defined login to check
     * @return true if login is unique
     * @throws NotUniqueLoginException if login exists in DB
     */
    public boolean checkUniqueLogin(String login) throws NotUniqueLoginException {
        LOG.trace("checkUniqueLogin({})", login);

       User user =  userRepository.getUserByLogin(login);

        if (user == null) {
            return true;
        }

        throw new NotUniqueLoginException(login);
    }

    /**
     * Creates new row with user data in DB
     *
     * @param user object to be created
     */
    public void registerNewUser(User user) {
        LOG.trace("checkUniqueLogin({})", user.getLogin());

        userRepository.save(user);
    }
}
