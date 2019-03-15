package model.service;

import exception.NoSuchIdException;
import exception.NotUniqueLoginException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum RegisterService {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(LoginService.class);
    private DaoFactory daoFactory;
    private UserDao userDao;

    RegisterService() {
        this.daoFactory = JDBCDaoFactory.getInstance();
        this.userDao = daoFactory.createUserDao();
    }

    public boolean checkUniqueLogin(String login) throws NotUniqueLoginException {
        try {
            userDao.findById(login);
        } catch (NoSuchIdException e) {
            return true;
        }

        throw new NotUniqueLoginException(login);
    }

    public void registerNewUser(User user) {
        userDao.create(user);
    }
}