package unsw.loopmania.Goals;

import unsw.loopmania.LoopManiaWorld;

public class XpLeaf implements Goal {
    private int goalXp;

    public XpLeaf(int goalXp) {
        this.goalXp = goalXp;
    }

    public void setGoalXp(int goalXp) {
        this.goalXp = goalXp;
    }

    public int getGoalXp() {
        return goalXp;
    }
    
     /**
     * Checks if the current xp in game cycle is greater than the goal xp
     * @param world the current world
     * @return boolean 
     */
    @Override
    public boolean metGoal(LoopManiaWorld world) { 
        if (world.getCharacter().getXpProperty().get() > goalXp) {
            return true;
        } 
        return false;
    }
    
}
