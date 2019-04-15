package ua.den.model.dao.jdbc;

import ua.den.model.dao.BonuseDao;
import ua.den.model.entity.dto.Bonuse;
import ua.den.model.exception.NoSuchIdException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class JDBCDaoBonuseTest {
    private BonuseDao bonuseDao;
    @Mock
    private Connection connection = Mockito.mock(Connection.class);
    @Mock
    private PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    @Mock
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    private Bonuse bonuse;

    @Before
    public void setUp() throws Exception {
        bonuseDao = new JDBCDaoBonuse(connection);
        bonuse = new Bonuse();

        bonuse.setId(23);
        bonuse.setName("pool");

        Mockito.when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(resultSet.getInt("bo_id")).thenReturn(bonuse.getId());
        Mockito.when(resultSet.getString("bonuses.name")).thenReturn(bonuse.getName());
    }

    @Test
    public void findById() throws NoSuchIdException {
        Bonuse receivedBonus = bonuseDao.findById(bonuse.getId());
        assertEquals(bonuse.getId(), receivedBonus.getId());
    }

    @Test
    public void create() {
        bonuseDao.create(bonuse);
    }

    @Test
    public void findAll() {
        List<Bonuse> bonuseList = bonuseDao.findAll();
        assertEquals(bonuseList.get(0), bonuse);
    }

    @Test
    public void update() {
        bonuseDao.update(bonuse);
    }

    @Test
    public void delete() {
        bonuseDao.delete(bonuse);
    }
}