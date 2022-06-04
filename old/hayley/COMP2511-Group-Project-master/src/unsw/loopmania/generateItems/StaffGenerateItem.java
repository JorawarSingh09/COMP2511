package unsw.loopmania.generateItems;

import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.items.Staff;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StaffGenerateItem extends GenerateItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public StaffGenerateItem() {
        this.name = new SimpleStringProperty("Staff");
        this.description = new SimpleStringProperty("Very low damage, but can randomly inflict a trance on Enemies to turn them into Allied Soldiers");
        this.price = new SimpleIntegerProperty(20);
        setType(ItemType.WEAPON);
    }

    /**
     * Creates a new armour item based on its new position
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return Item a new item
     */
    public Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item newStaff = new Staff(x, y);
        return newStaff;
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
