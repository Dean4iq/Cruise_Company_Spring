package ua.den.model.entity.tables;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExcursionTest {
    private Excursion excursion;

    private int id = 5;
    private String information = "marokko";
    private int price = 125;
    private int harborId = 3;

    private Harbor harbor;

    @Before
    public void setUp() throws Exception {
        excursion = new Excursion();
        harbor = new Harbor();

        harbor.setId(11);
        harbor.setName("yokohama");

        excursion.setId(id);
        excursion.setInformation(information);
        excursion.setPrice(price);
        excursion.setHarborId(harborId);

        excursion.setHarbor(harbor);
    }

    @Test
    public void getId() {
        assertEquals(id, excursion.getId());
    }

    @Test
    public void setId() {
        id = 45;

        excursion.setId(id);

        assertEquals(id, excursion.getId());
    }

    @Test
    public void getInformation() {
        assertEquals(information, excursion.getInformation());
    }

    @Test
    public void setInformation() {
        information = "marseille";

        excursion.setInformation(information);

        assertEquals(information, excursion.getInformation());
    }

    @Test
    public void getPrice() {
        assertEquals(price, excursion.getPrice());
    }

    @Test
    public void setPrice() {
        price = 123;

        excursion.setPrice(price);

        assertEquals(price, excursion.getPrice());
    }

    @Test
    public void getHarborId() {
        assertEquals(harborId, excursion.getHarborId());
    }

    @Test
    public void setHarborId() {
        harborId = 4;

        excursion.setHarborId(harborId);

        assertEquals(harborId, excursion.getHarborId());
    }

    @Test
    public void getHarbor() {
        assertEquals(harbor, excursion.getHarbor());
    }

    @Test
    public void setHarbor() {
        harbor = new Harbor();
        harbor.setId(harborId);
        harbor.setName("alexandria");

        excursion.setHarbor(harbor);

        assertEquals(harbor, excursion.getHarbor());
    }

    @Test
    public void equals() {
        Excursion testExcursion = excursion;

        assertEquals(excursion, testExcursion);
    }
}