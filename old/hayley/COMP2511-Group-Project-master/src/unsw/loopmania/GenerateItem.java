package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class GenerateItem {
    private ItemType type;

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public abstract Item createItem(SimpleIntegerProperty x, SimpleIntegerProperty y);
    public abstract SimpleStringProperty description();
    public abstract SimpleStringProperty name();
    public abstract SimpleIntegerProperty price();
}
