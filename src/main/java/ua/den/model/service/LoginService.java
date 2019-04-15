package ua.den.model.service;

import ua.den.model.exception.InvalidLoginOrPasswordException;
import ua.den.model.entity.dto.User;
import ua.den.model.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class {@code LoginService} provides methods to receive data from DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
@Service
public class LoginService {
    private static final Logger LOG = LogManager.getLogger(LoginService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * checks user data on login process
     *
     * @param user to be checked
     * @return user from DB after successful check
     * @throws InvalidLoginOrPasswordException if passwords from form and DB will be mismatched
     */
    public User checkUserData(User user) throws InvalidLoginOrPasswordException {
        LOG.trace("checkUserData({})", user.getLogin());

        User userInDB = userRepository.getOne(user.getLogin());

        if (userInDB.getLogin().equals(user.getLogin())
                && userInDB.getPassword().equals(user.getPassword())) {
            return userInDB;
        }

        throw new InvalidLoginOrPasswordException();
    }
}
