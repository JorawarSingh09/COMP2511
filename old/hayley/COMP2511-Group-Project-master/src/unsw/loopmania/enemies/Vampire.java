package unsw.loopmania.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.generateItems.StakeGenerateItem;
import unsw.loopmania.generateItems.TheOneRingGenerateItem;
import unsw.loopmania.generateItems.AndurilGenerateItem;
import unsw.loopmania.generateItems.TreeStumpGenerateItem;
import unsw.loopmania.generateItems.ShieldGenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.generateItems.ReversePathPotionGenerateItem;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.generateCards.VillageGenerateCard;
import unsw.loopmania.generateCards.BarracksGenerateCard;
import unsw.loopmania.generateCards.CampfireGenerateCard;
import unsw.loopmania.generateCards.TowerGenerateCard;
import unsw.loopmania.generateCards.TrapGenerateCard;
import unsw.loopmania.generateCards.VampireCastleGenerateCard;

import org.javatuples.Pair;

public class Vampire extends BasicEnemy {

    private double criticalHitChance = 0.1;

    public Vampire(PathPosition position) {
        super(position);
        setDamage(3);
        setBattleRadius(2);
        setHealth(15);
        setSupportRadius(3);
        List<Pair<GenerateItem, Double>> droppableItems = new ArrayList<Pair<GenerateItem, Double>>();
        droppableItems.add(new Pair<GenerateItem, Double>(new StakeGenerateItem(), 0.50));
        droppableItems.add(new Pair<GenerateItem, Double>(new ShieldGenerateItem(), 0.50));
        droppableItems.add(new Pair<GenerateItem, Double>(new HealthPotionGenerateItem(), 0.2));
        droppableItems.add(new Pair<GenerateItem, Double>(new TheOneRingGenerateItem(), 0.05));
        droppableItems.add(new Pair<GenerateItem, Double>(new AndurilGenerateItem(), 0.05));
        droppableItems.add(new Pair<GenerateItem, Double>(new TreeStumpGenerateItem(), 0.05));
        droppableItems.add(new Pair<GenerateItem, Double>(new ReversePathPotionGenerateItem(), 0.02));
        setDroppableItems(droppableItems);
        // card drops
        List<Pair<GenerateCard, Double>> droppableCards = new ArrayList<Pair<GenerateCard, Double>>();
        droppableCards.add(new Pair<GenerateCard, Double>(new TrapGenerateCard(), 0.05)); 
        droppableCards.add(new Pair<GenerateCard, Double>(new VillageGenerateCard(), 0.05));
        droppableCards.add(new Pair<GenerateCard, Double>(new CampfireGenerateCard(), 0.30));
        droppableCards.add(new Pair<GenerateCard, Double>(new BarracksGenerateCard(), 0.05));
        droppableCards.add(new Pair<GenerateCard, Double>(new TowerGenerateCard(), 0.05));
        droppableCards.add(new Pair<GenerateCard, Double>(new VampireCastleGenerateCard(), 0.30));
        setDroppableCards(droppableCards);
        // xp and gold
        setMaxGoldGained(8);
        setExperienceGained(20);
    }

    /**
     * Will reset the damage if is hit
     * @param damage Updating damage
     */
    @Override
    public void setDamage(int damage) {
        super.setDamage(damage);
    }

    /**
     * Potential chance for critical attack, doubling the damage
     * @param character current character which the enemy is battling
     */
    @Override
    public void attack(Character character) {
        super.attack(character);
        Random random = new Random(System.currentTimeMillis());
        if (random.nextInt(1000) < 1000 * criticalHitChance) {
            super.attack(character);
        }
    }


    /**
     * Sets critical hit chance
     * @param criticalHitChance The new critical hit chance
     */
    public void setCriticalHitChance(double criticalHitChance) {
      this.criticalHitChance = criticalHitChance;
    }

    /**
     * Gets the chance of a critical hit 
     * @return double 
     */
    public double getCriticalHitChance() {
        return this.criticalHitChance;
    }
}
