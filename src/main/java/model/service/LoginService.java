package model.service;

import model.exception.InvalidLoginOrPasswordException;
import model.exception.NoSuchIdException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class {@code LoginService} provides methods to receive data from DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
public class LoginService {
    private static final Logger LOG = LogManager.getLogger(LoginService.class);
    private DaoFactory daoFactory;
    private UserDao userDao;

    private LoginService() {
        this.daoFactory = JDBCDaoFactory.getInstance();
    }

    private static class SingletonHolder {
        private static final LoginService instance = new LoginService();
    }

    public static LoginService getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * checks user data on login process
     *
     * @param user to be checked
     * @return user from DB after successful check
     * @throws InvalidLoginOrPasswordException if passwords from form and DB will be mismatched
     * @throws NoSuchIdException               if there will be no user with defined login
     */
    public User checkUserData(User user) throws InvalidLoginOrPasswordException, NoSuchIdException {
        LOG.trace("checkUserData({})", user.getLogin());

        this.userDao = daoFactory.createUserDao();

        try {
            User userInDB = userDao.findById(user.getLogin());

            if (userInDB.getLogin().equals(user.getLogin())
                    && userInDB.getPassword().equals(user.getPassword())) {
                return userInDB;
            }
        } finally {
            try {
                userDao.close();
            } catch (Exception e) {
                LOG.error(e);
            }
        }

        throw new InvalidLoginOrPasswordException();
    }
}
