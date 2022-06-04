package unsw.loopmania.generateCards;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.Card;
import unsw.loopmania.cards.VillageCard;

public class VillageGenerateCard extends GenerateCard {
    public VillageGenerateCard() {
        super();
    }

    /**
     * Creates a new village card on the board
     * @param x x coordinate of position
     * @param y y coordinate of position
     */
    public Card createCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new VillageCard(x, y);
    }
}
