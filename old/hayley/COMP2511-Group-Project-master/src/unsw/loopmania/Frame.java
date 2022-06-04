package unsw.loopmania;

import javafx.scene.image.Image;

public class Frame {
    private double characterHealth;
    private double enemyHealth;
    private double bossHealth;
    private BasicEnemy enemy;
    private BasicEnemy boss;
    private int enemiesLeft;
    private int numOfAlliedSoldiers;

    public Frame(double characterHealth, double enemyHealth, double bossHealth, BasicEnemy enemy, BasicEnemy boss, int enemiesLeft, int numOfAlliedSoldiers) {
        this.characterHealth = characterHealth;
        this.enemyHealth = enemyHealth;
        this.enemy = enemy;
        this.boss = boss;
        this.enemiesLeft = enemiesLeft;
        this.numOfAlliedSoldiers = numOfAlliedSoldiers;
        this.bossHealth = bossHealth;
    }

    public double getCharacterHealth() {
        return this.characterHealth;
    }

    public double getEnemyHealth() {
        return this.enemyHealth;
    }

    public double getBossHealth() {
        return this.bossHealth;
    }

    public BasicEnemy getEnemy() {
        return this.enemy;
    }

    public BasicEnemy getBoss() {
        if (this.boss != null) {
            return this.boss;
        } else {
            return null;
        }
    }

    public int getEnemiesLeft() {
        return this.enemiesLeft;
    }

    public int getNumOfAlliedSoldiers() { 
        return this.numOfAlliedSoldiers;
    }


}
