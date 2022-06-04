package unsw.loopmania;

import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.generateItems.ArmourGenerateItem;
import unsw.loopmania.generateItems.HelmetGenerateItem;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.generateItems.StaffGenerateItem;
import unsw.loopmania.generateItems.StakeGenerateItem;
import unsw.loopmania.generateItems.SwordGenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;

import java.util.ArrayList;

public class HerosCastleMenu {
    private List<GenerateItem> items;

    public HerosCastleMenu() {
        items = new ArrayList<GenerateItem>();
        items.add(new SwordGenerateItem());
        items.add(new StaffGenerateItem());
        items.add(new StakeGenerateItem());
        items.add(new HelmetGenerateItem());
        items.add(new ArmourGenerateItem());
        items.add(new ShieldGenerateItem());
        items.add(new HealthPotionGenerateItem());
    }   

    /**
     * return a list of all items in the shop
     * @return List<GenerateItem>
     */
    public List<GenerateItem> getItems() {
        return items;
    }

    /**
     * Add a new item to the shop
     * @param newItem
     */
    public void addItem(GenerateItem newItem) {
        items.add(newItem);
    }

    /**
     * Checks if the shop items contains a specific item
     * @param item a specified item
     * @return boolean
     */
    public boolean hasItem(GenerateItem item) {
        return items.contains(item);
    }

    /**
     * Based on the price nad characters gold 
     * Makes a new item and adds it to inventory 
     * Returns the new item
     * @param character the current character
     * @param item the item to purchase
     * @param x x coordinate of position
     * @param y y coordinate of position
     * @return Item the new item
     */
    public Item purchaseItem(Character character, GenerateItem item, SimpleIntegerProperty x, SimpleIntegerProperty y, GameMode gameMode) {
        if (gameMode.limitPurchase(character, item) == false) return null;
        
        if (character.getGold() >= item.price().get()) {
            Item newItem = item.createItem(x, y);
            character.addItemToInventory(newItem);
            character.addPurchase(item);
            character.deductGold(item.price().get());
            return newItem;
        }
        return null;
    }

    /**
     * removes the item from the characters inventory 
     * Increases the characters gold amount
     * @param character The current character
     * @param item A item to sell
     */
    public void sellItem(Character character, Item item) {
        character.removeItemFromInventory(item);
        character.addGold(item.getSellPrice().get());
        item.destroy();
    }
}
