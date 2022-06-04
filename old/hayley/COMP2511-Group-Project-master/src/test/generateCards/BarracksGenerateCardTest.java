package test.generateCards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Card;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.cards.BarracksCard;
import unsw.loopmania.generateCards.BarracksGenerateCard;


public class BarracksGenerateCardTest {
    @Test
    public void testConstructor() {
        GenerateCard generator = new BarracksGenerateCard();

        assertTrue(generator instanceof BarracksGenerateCard);
        assertTrue(generator instanceof GenerateCard);
    }   

    @Test
    public void testCreateCard() {
        GenerateCard generator = new BarracksGenerateCard(); 
        Card card = generator.createCard(new SimpleIntegerProperty(5), new SimpleIntegerProperty(4));

        assertTrue(card instanceof BarracksCard);
        assertEquals(5, card.getX());
        assertEquals(4, card.getY());
    }
}