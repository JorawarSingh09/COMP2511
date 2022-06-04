package unsw.loopmania.generateCards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.Card;
import unsw.loopmania.cards.CampfireCard;

public class CampfireGenerateCard extends GenerateCard {
    public CampfireGenerateCard() {
        super();
    }

    /**
     * Creates a new campfire card on the board
     * @param x x coordinate of position
     * @param y y coordinate of position
     */
    public Card createCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new CampfireCard(x, y);
    }
}
