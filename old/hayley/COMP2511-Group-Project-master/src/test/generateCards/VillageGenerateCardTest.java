package test.generateCards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Card;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.cards.VillageCard;
import unsw.loopmania.generateCards.VillageGenerateCard;


public class VillageGenerateCardTest {
    @Test
    public void testConstructor() {
        GenerateCard generator = new VillageGenerateCard();

        assertTrue(generator instanceof VillageGenerateCard);
        assertTrue(generator instanceof GenerateCard);
    }   

    @Test
    public void testCreateCard() {
        GenerateCard generator = new VillageGenerateCard(); 
        Card card = generator.createCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(0));

        assertTrue(card instanceof VillageCard);
        assertEquals(5, card.getX());
        assertEquals(0, card.getY());
    }
}
