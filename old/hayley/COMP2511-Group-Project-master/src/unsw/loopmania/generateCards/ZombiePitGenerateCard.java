package unsw.loopmania.generateCards;

import unsw.loopmania.cards.ZombiePitCard;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.Card;
import javafx.beans.property.SimpleIntegerProperty;

public class ZombiePitGenerateCard extends GenerateCard {
    public ZombiePitGenerateCard() {
        super();
    }

    /**
     * Creates a new zombie pit card on the board
     * @param x x coordinate of position
     * @param y y coordinate of position
     */
    public Card createCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        return new ZombiePitCard(x, y);
    }
}
