package unsw.loopmania.generateItems;

import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.items.Helmet;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HelmetGenerateItem extends GenerateItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public HelmetGenerateItem() {
        this.name = new SimpleStringProperty("Helmet");
        this.description = new SimpleStringProperty("Enemy damage is reduced, but character damage is also reduced.");
        this.price = new SimpleIntegerProperty(15);
        setType(ItemType.HELMET);
    }

     /**
     * Creates a new helemt item based on its new position
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return Item a new item
     */
    public Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item newHelmet = new Helmet(x, y);
        return newHelmet;
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
