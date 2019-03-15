package model.entity.enums;

public class RoomType {
    private int id;
    private String name;
    private int priceModifier;
    Bonuses[] bonuses;

    public int getPriceModifier() {
        return priceModifier;
    }

    public void setPriceModifier(int priceModifier) {
        this.priceModifier = priceModifier;
    }
}
