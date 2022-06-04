package unsw.loopmania.generateItems;

import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.RareItem;
import unsw.loopmania.items.TheOneRing;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TheOneRingGenerateItem extends GenerateItem implements RareItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public TheOneRingGenerateItem() {
        this.name = new SimpleStringProperty("The One Ring");
        this.description = new SimpleStringProperty("Player Respawns with full health one time.");
        this.price = new SimpleIntegerProperty(50);
        setType(ItemType.ACCESSORY);
    }

   /**
     * Creates a new the one ring item based on its new position
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return Item a new item
     */
    public Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item newTheOneRing = new TheOneRing(x, y);
        return newTheOneRing;
    }

    /**
     * Gets the description for the label
     * @return SimpleStringProperty
     */
    public SimpleStringProperty description() {
        return description;
    }

    /**
     * Gets the name for the label
     * @return SimpleStringProperty
     */
    public SimpleStringProperty name() {
        return name;
    }

    /**
     * Gets the price for the label
     * @return SimpleStringProperty
     */
    public SimpleIntegerProperty price() {
        return price;
    }
}