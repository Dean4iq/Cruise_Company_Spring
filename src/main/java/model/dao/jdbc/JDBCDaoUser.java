package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.UserDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoUser implements UserDao {
    private static final Logger LOG = LogManager.getLogger(JDBCDaoUser.class);
    private Connection connection;

    JDBCDaoUser(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(user.getClass(), SQLOperation.INSERT))) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getSurname());
            preparedStatement.setBoolean(6, user.isAdmin());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public User findById(String id) throws NoSuchIdException {
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(User.class, SQLOperation.FIND_BY_ID))) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = extractFromResultSet(resultSet);
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        if (user == null) {
            throw new NoSuchIdException(id);
        }

        return user;
    }

    static User extractFromResultSet(ResultSet resultSet) throws SQLException {
        User.Builder user = new User.Builder();

        user.login(resultSet.getString("user.login"))
                .password(resultSet.getString("user.password"))
                .email(resultSet.getString("user.email"))
                .name(resultSet.getString("user.name"))
                .surname(resultSet.getString("user.surname"))
                .admin(resultSet.getBoolean("user.admin"));

        return user.build();
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(User.class, SQLOperation.SELECT))) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userList.add(extractFromResultSet(resultSet));
            }
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }

        return userList;
    }

    @Override
    public void update(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(user.getClass(), SQLOperation.UPDATE))) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setBoolean(5, user.isAdmin());
            preparedStatement.setString(6, user.getLogin());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void delete(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(user.getClass(), SQLOperation.DELETE))) {
            preparedStatement.setString(1, user.getLogin());

            preparedStatement.executeUpdate();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void close() {
        ConnectorDB.INSTANCE.returnConnectionToPool(connection);
    }
}
