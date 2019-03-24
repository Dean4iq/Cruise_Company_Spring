package model.service;

import model.exception.InvalidLoginOrPasswordException;
import model.exception.NoSuchIdException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public User checkUserData(User user) throws InvalidLoginOrPasswordException, NoSuchIdException {
        LOG.trace("checkUserData({})", user.getLogin());

        this.userDao = daoFactory.createUserDao();

        User userInDB = userDao.findById(user.getLogin());

        try {
            userDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }

        if (userInDB.getLogin().equals(user.getLogin())
                && userInDB.getPassword().equals(user.getPassword())) {
            return userInDB;
        }

        throw new InvalidLoginOrPasswordException();
    }
}
