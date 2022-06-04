package unsw.loopmania.generateItems;

import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.items.Armour;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ArmourGenerateItem extends GenerateItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public ArmourGenerateItem() {
        this.name = new SimpleStringProperty("Armour");
        this.description = new SimpleStringProperty("Enemy Damage is halved.");
        this.price = new SimpleIntegerProperty(20);
        setType(ItemType.ARMOUR);
    }

    /**
     * Creates a new armour item based on its new position
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return Item a new item
     */
    public Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item newArmour = new Armour(x, y);
        return newArmour;
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
