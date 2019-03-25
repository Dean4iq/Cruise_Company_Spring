package model.service;

import model.exception.NoSuchIdException;
import model.exception.NotUniqueLoginException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class {@code RegisterService} provides methods to receive and create data from/to DB on
 * servlet commands demand
 *
 * @author Dean4iq
 * @version 1.0
 */
public class RegisterService {
    private static final Logger LOG = LogManager.getLogger(RegisterService.class);
    private DaoFactory daoFactory;
    private UserDao userDao;

    private RegisterService() {
        this.daoFactory = JDBCDaoFactory.getInstance();
    }

    private static class SingletonHolder {
        private static final RegisterService instance = new RegisterService();
    }

    public static RegisterService getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * Checks if user with defined login not exists in the system
     *
     * @param login defined login to check
     * @return true if login is unique
     * @throws NotUniqueLoginException if login exists in DB
     */
    public boolean checkUniqueLogin(String login) throws NotUniqueLoginException {
        LOG.trace("checkUniqueLogin({})", login);

        this.userDao = daoFactory.createUserDao();

        try {
            userDao.findById(login);
        } catch (NoSuchIdException e) {
            return true;
        } finally {
            try {
                userDao.close();
            } catch (Exception e) {
                LOG.error(e);
            }
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

        this.userDao = daoFactory.createUserDao();

        userDao.create(user);

        try {
            userDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
