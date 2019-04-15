package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountryTest {
    private Country country;

    private int id = 1;
    private String name = "ukr";

    @Before
    public void setUp() throws Exception {
        country = new Country();

        country.setId(id);
        country.setName(name);
    }

    @Test
    public void getId() {
        assertEquals(id, country.getId());
    }

    @Test
    public void setId() {
        int testId = 22;

        country.setId(testId);

        assertEquals(testId, country.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, country.getName());
    }

    @Test
    public void setName() {
        String name = "deu";

        country.setName(name);

        assertEquals(name, country.getName());
    }

    @Test
    public void equals() {
        Country testCountry = country;

        assertEquals(testCountry, country);
    }
}