package model.service;

import exception.InvalidLoginOrPasswordException;
import exception.NoSuchIdException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum LoginService {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(LoginService.class);
    private DaoFactory daoFactory;
    private UserDao userDao;

    LoginService() {
        this.daoFactory = JDBCDaoFactory.getInstance();
        this.userDao = daoFactory.createUserDao();
    }

    public User checkUserData(User user) throws InvalidLoginOrPasswordException, NoSuchIdException {
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
