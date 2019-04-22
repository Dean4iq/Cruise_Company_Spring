package ua.den.model.entity.tables;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BonuseTest {
    private Bonuse bonuse;

    private int id = 13;
    private String name = "Spa";

    @Before

    public void setUp() throws Exception {
        bonuse = new Bonuse();

        bonuse.setId(id);
        bonuse.setName(name);
    }

    @Test
    public void getId() {
        assertEquals(id, bonuse.getId());
    }

    @Test
    public void setId() {
        int testId = 31;

        bonuse.setId(testId);

        assertEquals(testId, bonuse.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, bonuse.getName());
    }

    @Test
    public void setName() {
        String testName = "Gym";

        bonuse.setName(testName);

        assertEquals(testName, bonuse.getName());
    }

    @Test
    public void equals() {
        Bonuse testBonuse = new Bonuse();

        testBonuse.setId(id);
        testBonuse.setName(name);

        assertEquals(bonuse, testBonuse);
    }

    @Test
    public void notEquals() {
        Bonuse testBonuse = new Bonuse();

        assertNotEquals(bonuse, testBonuse);
    }

    @Test
    public void notEqualsNull() {
        Bonuse testBonuse = null;

        assertNotEquals(bonuse, testBonuse);
    }
}