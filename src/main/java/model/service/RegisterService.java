package model.service;

import exception.NoSuchIdException;
import exception.NotUniqueLoginException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum RegisterService {
    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(RegisterService.class);
    private DaoFactory daoFactory;
    private UserDao userDao;

    RegisterService() {
        this.daoFactory = JDBCDaoFactory.getInstance();
    }

    public boolean checkUniqueLogin(String login) throws NotUniqueLoginException {
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

    public void registerNewUser(User user) {
        this.userDao = daoFactory.createUserDao();

        userDao.create(user);

        try {
            userDao.close();
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
