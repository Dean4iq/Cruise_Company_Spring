package ua.den.model.entity.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTypeBonuseTest {
    private RoomTypeBonuse roomTypeBonuse;

    private int roomTypeId = 5;
    private int bonuseId = 7;

    private RoomType roomType;
    private Bonuse bonuse;

    @Before
    public void setUp() throws Exception {
        roomTypeBonuse = new RoomTypeBonuse();

        roomType = new RoomType();
        bonuse = new Bonuse();

        roomType.setId(5);
        roomType.setPriceModifier(43);

        bonuse.setId(7);
        bonuse.setName("Cinema");

        roomTypeBonuse.setRoomTypeId(roomTypeId);
        roomTypeBonuse.setBonuseId(bonuseId);
        roomTypeBonuse.setRoomType(roomType);
        roomTypeBonuse.setBonuse(bonuse);
    }

    @Test
    public void getRoomTypeId() {
        assertEquals(roomTypeId, roomTypeBonuse.getRoomTypeId());
    }

    @Test
    public void setRoomTypeId() {
        roomTypeId = 2;

        roomTypeBonuse.setRoomTypeId(roomTypeId);

        assertEquals(roomTypeId, roomTypeBonuse.getRoomTypeId());
    }

    @Test
    public void getBonuseId() {
        assertEquals(bonuseId, roomTypeBonuse.getBonuseId());
    }

    @Test
    public void setBonuseId() {
        bonuseId = 3;

        roomTypeBonuse.setBonuseId(bonuseId);

        assertEquals(bonuseId, roomTypeBonuse.getBonuseId());
    }

    @Test
    public void getRoomType() {
        assertEquals(roomType, roomTypeBonuse.getRoomType());
    }

    @Test
    public void setRoomType() {
        RoomType roomTypeTest = new RoomType();
        roomTypeTest.setId(55);

        roomTypeBonuse.setRoomType(roomTypeTest);

        assertEquals(roomTypeTest, roomTypeBonuse.getRoomType());
    }

    @Test
    public void getBonuse() {

    }

    @Test
    public void setBonuse() {
        Bonuse testBonuse = new Bonuse();
        testBonuse.setId(66);

        roomTypeBonuse.setBonuse(testBonuse);

        assertEquals(testBonuse, roomTypeBonuse.getBonuse());
    }

    @Test
    public void equals() {
        RoomTypeBonuse roomTypeBonuseTest = roomTypeBonuse;

        assertEquals(roomTypeBonuse, roomTypeBonuseTest);
    }
}