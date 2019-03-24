package model.service;

import model.exception.NoSuchIdException;
import model.exception.NotUniqueLoginException;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.dao.jdbc.JDBCDaoFactory;
import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
