package model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HarborTest {
    private Harbor harbor;

    private int id = 6;
    private String name = "ukr.odesa";
    private int countryId = 6;

    private Country country;

    @Before
    public void setUp() throws Exception {
        harbor = new Harbor();
        country = new Country();

        country.setId(1);
        country.setName("ukr");

        harbor.setId(id);
        harbor.setName(name);
        harbor.setCountryId(countryId);
        harbor.setCountry(country);
    }

    @Test
    public void getId() {
        assertEquals(id, harbor.getId());
    }

    @Test
    public void setId() {
        id = 2;

        harbor.setId(id);

        assertEquals(id, harbor.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, harbor.getName());
    }

    @Test
    public void setName() {
        name = "ukr.mykolaiv";

        harbor.setName(name);

        assertEquals(name, harbor.getName());
    }

    @Test
    public void getCountryId() {
        assertEquals(countryId, harbor.getCountryId());
    }

    @Test
    public void setCountryId() {
        countryId = 55;

        harbor.setCountryId(countryId);

        assertEquals(countryId, harbor.getCountryId());
    }

    @Test
    public void getCountry() {
        assertEquals(country, harbor.getCountry());
    }

    @Test
    public void setCountry() {
        country = new Country();
        country.setId(88);
        country.setName("chi");

        harbor.setCountry(country);

        assertEquals(country, harbor.getCountry());
    }

    @Test
    public void equals() {
        Harbor testHarbor = harbor;

        assertEquals(harbor, testHarbor);
    }

    @Test
    public void notEquals() {
        Harbor testHarbor = new Harbor();
        testHarbor.setId(55);
        testHarbor.setName("kiel");

        assertNotEquals(harbor, testHarbor);
    }
}