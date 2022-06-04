package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Item extends StaticEntity {
    private SimpleIntegerProperty sellPrice;
    private ItemType type;

    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.sellPrice = new SimpleIntegerProperty(1);
        this.type = ItemType.NOT_EQUIPPABLE;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public GenerateItem getItemDetails() {
        return null;
    }

    /**
     * Sell item price setter
     * @param price The price to sell an item
     */
    public void setSellPrice(int price) {
        sellPrice.set(price);
    }

    /**
     * Sell item price getter
     * @return SimpleIntegerProperty
     */
    public SimpleIntegerProperty getSellPrice() {
        return sellPrice;
    }
}
