package unsw.loopmania.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.BattleBehaviourContext;
import unsw.loopmania.Character;
import unsw.loopmania.CharacterEffect;
import unsw.loopmania.PathPosition;
import unsw.loopmania.EnemyPositionObserver;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.Frame;
import unsw.loopmania.GenerateItem;
import unsw.loopmania.generateItems.DoggieCoinGenerateItem;

import org.javatuples.Pair;

public class Doggie extends BasicEnemy implements BattleBehaviourContext {
    private double stunChance = 0.1;

    public Doggie(PathPosition position) {
        super(position);
        setDamage(5);
        setBattleRadius(1);
        setHealth(60);
        setSupportRadius(2);
        List<Pair<GenerateItem, Double>> droppableItems = new ArrayList<Pair<GenerateItem, Double>>();
        droppableItems.add(new Pair<GenerateItem, Double>(new DoggieCoinGenerateItem(), 1.0));
        setDroppableItems(droppableItems);
        // xp and gold
        setMaxGoldGained(50);
        setExperienceGained(300);
    }

    /**
     * Doggie Battle Behavviour
     * @param enemies the list of supporting enemies
     * @param boss the boss
     * @param character the character
     * @param baseCharacterHealth the health of the character after all items are taken into account.
     */
    public List<Frame> battleBehaviour(List<BasicEnemy> enemies, BasicEnemy boss, Character character, int baseCharacterHealth) {
        List<Frame> frames = new ArrayList<>();
        int initialBossHealth = getHealth();
        // Add initial frame
        int index = 0;
        if (enemies.size() > 0) {
            frames.add(new Frame(((double) character.getCurrentHealth() / baseCharacterHealth), 1, ((double) getHealth() / initialBossHealth), enemies.get(0), this, enemies.size() - index, character.getNumOfAlliedSoldiers()));
        }
        for (BasicEnemy enemy : enemies) {
        // Initial set up for each enemy
            for (EquippableItem item : character.getEquippedItems()) {
                item.affect(enemy);
            }
            double baseEnemyBattleHealth = enemy.getHealth();
            while (character.isAlive() && enemy.isAlive()) {
                Random rand = new Random();
                if (rand.nextInt() > stunChance || !isAlive()) {
                    character.attack(enemy);
                    character.attack(this);
                }
                if (enemy.isAlive()) {
                    enemy.attack(character);
                }
                if (isAlive()) {
                    attack(character);
                    frames.add(new Frame(((double) character.getCurrentHealth() / baseCharacterHealth), enemy.getHealth() / baseEnemyBattleHealth, ((double) getHealth() / initialBossHealth), enemy, this, enemies.size() - index, character.getNumOfAlliedSoldiers()));
                } else {
                    frames.add(new Frame(((double) character.getCurrentHealth() / baseCharacterHealth), enemy.getHealth() / baseEnemyBattleHealth, 0, enemy, null, enemies.size() - index, character.getNumOfAlliedSoldiers()));
                }
            }
            index++;
        }
        while (character.isAlive() && isAlive()) {
            Random rand = new Random();
            if (rand.nextInt() > stunChance || !isAlive()) {
                character.attack(this);
            }
            attack(character);
            frames.add(new Frame(((double) character.getCurrentHealth() / baseCharacterHealth), (double) getHealth() / initialBossHealth, 0, this, null, 1, character.getNumOfAlliedSoldiers()));
        }
        return frames;
    }
}
