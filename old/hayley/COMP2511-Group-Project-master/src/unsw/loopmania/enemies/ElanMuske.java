package unsw.loopmania.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.BattleBehaviourContext;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.EquippableItem;
import unsw.loopmania.Frame;
import unsw.loopmania.GenerateItem;
import org.javatuples.Pair;

public class ElanMuske extends BasicEnemy implements BattleBehaviourContext {
    private double healChance = 0.1;

    public ElanMuske(PathPosition position) {
        super(position);
        setDamage(10);
        setBattleRadius(1);
        setHealth(120);
        setSupportRadius(2);
        List<Pair<GenerateItem, Double>> droppableItems = new ArrayList<Pair<GenerateItem, Double>>();
        setDroppableItems(droppableItems);
        // xp and gold
        setMaxGoldGained(200);
        setExperienceGained(600);
    }

    /**
     * Will reset the damage if is hit
     */
    @Override
    public void setDamage(int damage) {
        super.setDamage(damage);
    }
    
    /**
     * Elon Musk Battle Behavviour
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
                character.attack(enemy);
                character.attack(this);
                Random rand = new Random();
                if (rand.nextInt() > healChance && isAlive()) {
                    enemy.setHealth((int) baseEnemyBattleHealth);
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
            attack(character);
            character.attack(this);
            frames.add(new Frame(((double) character.getCurrentHealth() / baseCharacterHealth), (double) getHealth() / initialBossHealth, 0, this, null, 1, character.getNumOfAlliedSoldiers()));
        }
        return frames;
    }
}

