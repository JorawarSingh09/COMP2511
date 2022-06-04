package unsw.loopmania.generateItems;

import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.items.Stake;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StakeGenerateItem extends GenerateItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public StakeGenerateItem() {
        this.name = new SimpleStringProperty("Stake");
        this.description = new SimpleStringProperty("Lower Damage, but very high damage against Vampires.");
        this.price = new SimpleIntegerProperty(15);
        setType(ItemType.WEAPON);
    }

    /**
     * Creates a new stake item based on its new position
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return Item a new item
     */
    public Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item newStake = new Stake(x, y);
        return newStake;
    }

    /**
     * Gets the description for the label
     * @return SimpleStringProperty
     */
    public SimpleStringProperty description() {
        return description;
    }

    /**
     * Gets the price for the label
     * @return SimpleStringProperty
     */
    public SimpleIntegerProperty price() {
        return price;
    }

    /**
     * Gets the name for the label
     * @return SimpleStringProperty
     */
    public SimpleStringProperty name() {
        return name;
    }
}