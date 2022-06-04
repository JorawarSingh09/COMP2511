package unsw.loopmania.Goals;

import unsw.loopmania.LoopManiaWorld;

public class CycleLeaf implements Goal{
    private int goalCycle; 

    public CycleLeaf(int goalCycle) {
        this.goalCycle = goalCycle;
    }

    
    /**
     * Checks if the current cycle in game cycle is greater than the goal
     * @param world the current world
     * @return boolean 
     */
    @Override
    public boolean metGoal(LoopManiaWorld world) {
        if (world.getGameCycle() > goalCycle) {
            return true;
        } 
        return false;
        
    }
    
}
