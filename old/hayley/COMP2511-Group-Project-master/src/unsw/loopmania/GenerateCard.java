package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class GenerateCard {
    public abstract Card createCard(SimpleIntegerProperty x, SimpleIntegerProperty y);
}
