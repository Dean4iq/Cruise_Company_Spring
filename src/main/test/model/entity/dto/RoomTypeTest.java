package model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomTypeTest {
    private RoomType roomType;

    private int id = 7;
    private String name = "Lux";
    private int priceModifier = 77;
    private List<Bonuse> bonuses;

    @Before
    public void setUp() throws Exception {
        roomType = new RoomType();
        bonuses = new ArrayList<>();

        bonuses.add(new Bonuse());

        roomType.setId(id);
        roomType.setName(name);
        roomType.setPriceModifier(priceModifier);
        roomType.setBonuses(bonuses);
    }

    @Test
    public void getPriceModifier() {
        assertEquals(priceModifier, roomType.getPriceModifier());
    }

    @Test
    public void setPriceModifier() {
        priceModifier = 8;

        roomType.setPriceModifier(priceModifier);

        assertEquals(priceModifier, roomType.getPriceModifier());
    }

    @Test
    public void getId() {
        assertEquals(id, roomType.getId());
    }

    @Test
    public void setId() {
        id = 8;

        roomType.setId(id);

        assertEquals(id, roomType.getId());
    }

    @Test
    public void getName() {
        assertEquals(name, roomType.getName());
    }

    @Test
    public void setName() {
        name = "Economic";

        roomType.setName(name);

        assertEquals(name, roomType.getName());
    }

    @Test
    public void getBonuses() {
        assertEquals(bonuses, roomType.getBonuses());
    }

    @Test
    public void setBonuses() {
        bonuses = new ArrayList<>();

        bonuses.add(new Bonuse());

        assertEquals(bonuses, roomType.getBonuses());
    }

    @Test
    public void equals() {
        RoomType testRoomType = roomType;

        assertEquals(testRoomType, roomType);
    }
}