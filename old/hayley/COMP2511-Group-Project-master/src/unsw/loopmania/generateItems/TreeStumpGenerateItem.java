package unsw.loopmania.generateItems;

import unsw.loopmania.Item;
import unsw.loopmania.ItemType;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.RareItem;
import unsw.loopmania.items.TreeStump;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TreeStumpGenerateItem extends GenerateItem implements RareItem {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;

    public TreeStumpGenerateItem() {
        this.name = new SimpleStringProperty("Tree Stump");
        this.description = new SimpleStringProperty("An especially powerful shield, which provides higher defence against bosses.");
        this.price = new SimpleIntegerProperty(50);
        setType(ItemType.SHIELD);
    }

    public Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        Item newTreeStump = new TreeStump(x, y);
        return newTreeStump;
    }

    public SimpleStringProperty description() {
        return description;
    }

    public SimpleStringProperty name() {
        return name;
    }

    public SimpleIntegerProperty price() {
        return price;
    }
}