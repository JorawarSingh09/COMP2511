package unsw.loopmania.items;

import unsw.loopmania.EquippableItem;
import unsw.loopmania.RareItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.ItemType;
import unsw.loopmania.generateItems.*;
import javafx.beans.property.SimpleIntegerProperty;

public class TheOneRing extends EquippableItem implements RareItem {
    private GenerateItem itemInfo = new TheOneRingGenerateItem();

    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        setSellPrice(50);
        setType(ItemType.ACCESSORY);
    }

    /**
     * Gets the image of the one ring for rendering
     * @return Image
     */
    @Override
    public GenerateItem getItemDetails() {
        return itemInfo;
    }

    @Override
    public String toString() {
        return "The One Ring";
    }
}
