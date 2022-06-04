package unsw.loopmania.enemies;

import java.util.ArrayList;
import java.util.List;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.GenerateCard;
import unsw.loopmania.generateCards.BarracksGenerateCard;
import unsw.loopmania.generateCards.CampfireGenerateCard;
import unsw.loopmania.generateCards.TowerGenerateCard;
import unsw.loopmania.generateCards.TrapGenerateCard;
import unsw.loopmania.generateCards.VampireCastleGenerateCard;
import unsw.loopmania.generateCards.VillageGenerateCard;
import unsw.loopmania.generateCards.ZombiePitGenerateCard;
import unsw.loopmania.generateItems.TheOneRingGenerateItem;
import unsw.loopmania.generateItems.AndurilGenerateItem;
import unsw.loopmania.generateItems.TreeStumpGenerateItem;
import unsw.loopmania.generateItems.HealthPotionGenerateItem;
import unsw.loopmania.generateItems.ReversePathPotionGenerateItem;
import unsw.loopmania.PathPosition;
import unsw.loopmania.generateItems.StaffGenerateItem;

import org.javatuples.Pair;

public class Zombie extends BasicEnemy {

    public Zombie(PathPosition position) {
        super(position);
        setDamage(2);
        setBattleRadius(2);
        setHealth(10);
        setSupportRadius(2);
        List<Pair<GenerateItem, Double>> droppableItems = List.of(
            Pair.with(new StaffGenerateItem(), 0.50),
            Pair.with(new HealthPotionGenerateItem(), 0.2),
            Pair.with(new TheOneRingGenerateItem(), 0.05),
            Pair.with(new AndurilGenerateItem(), 0.05),
            Pair.with(new TreeStumpGenerateItem(), 0.05),
            Pair.with(new ReversePathPotionGenerateItem(), 0.02)
        );
        setDroppableItems(droppableItems);
        // card drops
        List<Pair<GenerateCard, Double>> droppableCards = new ArrayList<Pair<GenerateCard, Double>>();
        droppableCards.add(new Pair<GenerateCard, Double>(new BarracksGenerateCard(), 0.05));
        droppableCards.add(new Pair<GenerateCard, Double>(new ZombiePitGenerateCard(), 0.30));
        droppableCards.add(new Pair<GenerateCard, Double>(new CampfireGenerateCard(), 0.05));
        droppableCards.add(new Pair<GenerateCard, Double>(new TowerGenerateCard(), 0.30));
        droppableCards.add(new Pair<GenerateCard, Double>(new VillageGenerateCard(), 0.05));
        droppableCards.add(new Pair<GenerateCard, Double>(new TrapGenerateCard(), 0.30));

        setDroppableCards(droppableCards);
        // xp and gold
        setMaxGoldGained(4);
        setExperienceGained(10);
    }

    /**
     * The zombie moves every two ticks
     */
    @Override
    public void move(int tick) {
        if (tick % 2 == 0) {
            super.move(tick);
        }
    }
}