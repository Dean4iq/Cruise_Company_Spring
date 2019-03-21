package model.dao.jdbc;

import exception.AnnotationAbsenceException;
import exception.NoSuchIdException;
import model.dao.TicketExcursionDao;
import model.dao.util.SQLOperation;
import model.dao.util.SqlReflector;
import model.entity.dto.TicketExcursion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JDBCDaoTicketExcursion implements TicketExcursionDao {
    public static final Logger LOG = LogManager.getLogger(JDBCDaoTicketExcursion.class);
    private Connection connection;

    public JDBCDaoTicketExcursion(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TicketExcursion findById(TicketExcursion ticketExcursion) throws NoSuchIdException {
        return null;
    }

    @Override
    public void addList(List<TicketExcursion> ticketExcursionList) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                new SqlReflector().process(TicketExcursion.class, SQLOperation.INSERT))) {
            connection.setAutoCommit(false);

            for (TicketExcursion ticketExcursion : ticketExcursionList) {
                preparedStatement.setInt(1, ticketExcursion.getTicketId());
                preparedStatement.setInt(2, ticketExcursion.getExcursionId());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

            connection.commit();
        } catch (OperationNotSupportedException | AnnotationAbsenceException | SQLException e) {
            LOG.error(e);
        }
    }

    @Override
    public void create(TicketExcursion entity) {

    }

    @Override
    public List<TicketExcursion> findAll() {
        return null;
    }

    @Override
    public void update(TicketExcursion entity) {

    }

    @Override
    public void delete(TicketExcursion entity) {

    }

    @Override
    public void close() throws Exception {
        ConnectorDB.INSTANCE.returnConnectionToPool(connection);
    }
}
