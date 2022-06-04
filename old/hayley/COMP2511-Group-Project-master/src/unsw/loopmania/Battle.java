package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

public class Battle implements BattleBehaviourContext {
    private Character character;
    private List<BasicEnemy> enemies;
    private List<CharacterEffect> buildings;
    private int initialCharacterHealth;
    private int initialCharacterDamage;
    private double baseEnemyBattleHealth;
    private BasicEnemy boss;
    private BattleBehaviourContext battleBehaviourState;

    public Battle(Character character, List<BasicEnemy> enemies, List<CharacterEffect> buildings, BasicEnemy boss) {
        this.character = character;
        this.enemies = enemies;
        this.buildings = buildings;
        this.initialCharacterHealth = character.getCurrentHealth();
        this.initialCharacterDamage = character.getDamage();
        this.boss = boss;
        if (boss != null) {
            battleBehaviourState = (BattleBehaviourContext) boss;
        } else {
            battleBehaviourState = this;
        }
    }

    /**
     * Default battle behaviour (ie no bosses)
     * @param enemies
     * @param buildings
     * @param boss
     * @return frames to animate battle
     */
    public List<Frame> battleBehaviour(List<BasicEnemy> enemies,  BasicEnemy boss, Character character, int baseCharacterHealth) {
        List<Frame> frames = new ArrayList<>();
        // Add initial frame
        int index = 0;
        frames.add(new Frame((double) character.getCurrentHealth() / baseCharacterHealth, 1.0, 0, enemies.get(0), null, enemies.size() - index, character.getNumOfAlliedSoldiers()));
        for (BasicEnemy enemy : enemies) {
        // Initial set up for each enemy
            for (EquippableItem item : character.getEquippedItems()) {
                item.affect(enemy);
            }
            this.baseEnemyBattleHealth = enemy.getHealth();
            while (character.isAlive() && enemy.isAlive()) {
                character.attack(enemy);
                if (enemy.isAlive()) {
                    enemy.attack(character);
                }
                int enemyHealth = enemy.getHealth();
                if (enemyHealth < 0) enemyHealth = 0;
                frames.add(new Frame(((double) character.getCurrentHealth() / baseCharacterHealth), enemyHealth / baseEnemyBattleHealth, 0, enemy, null, enemies.size() - index, character.getNumOfAlliedSoldiers()));
            }
            index++;
        }
        return frames;
    }

    /**
     * Creates the frames to run the battle
     * Sets up both the character and enemy
     * Updates both enemy and character stats during battle until one is dead
     * @return List<Triplet<Integer, Integer, BasicEnemy>>
     */
    public List<Frame> runBattle() {
        // Initial set up for character
        for (EquippableItem item : character.getEquippedItems()) {
            item.affect(character);
        }
        for (CharacterEffect building : buildings) {
            building.affect(character);
        }
        int baseCharacterHealth = character.getCurrentHealth() + (character.getBaseHealth() - initialCharacterHealth);
        return battleBehaviourState.battleBehaviour(enemies, boss, character, baseCharacterHealth);
    }

    /**
     * Resets the characters health and stats to before effects were applied
     */
    public void resetCharacter() {
        character.setDamage(initialCharacterDamage);
        character.cleanAlliedSoldiers();
        if (initialCharacterHealth < character.getCurrentHealth()) {
            character.setCurrentHealth(initialCharacterHealth);
        }
    }

    /** 
     * Check if the character is still alive at the end of the battle
     * @return boolean
     */
    public boolean wonBattle() {
        return character.isAlive();
    }
}
