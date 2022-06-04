package unsw.loopmania.Goals;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.loopmania.LoopManiaWorld;

public class GoalAND implements Goal {
    private Goal goal1;
    private Goal goal2;

    public GoalAND(JSONArray subgoals) {
        this.goal1 = parseJson(subgoals.getJSONObject(0));
        this.goal2 = parseJson(subgoals.getJSONObject(1));
    }

    /**
     * Takes in an array of objects 
     * recusivley gets the quantity of each array element/ goal
     * @param json an array of AND json objects
     * @return Goal
     */
    public Goal parseJson(JSONObject json) {
        Goal goalObject;
        switch (json.getString("goal")) {
            case "experience":
                goalObject = new XpLeaf(json.getInt("quantity"));
                break;

            case "cycles":
                goalObject = new CycleLeaf(json.getInt("quantity"));
                break;

            case "gold":
                goalObject = new GoldLeaf(json.getInt("quantity"));
                break;

            case "AND":
                goalObject = new GoalAND(json.getJSONArray("subgoals"));
                break;

            case "OR":
                goalObject = new GoalOR(json.getJSONArray("subgoals"));
                break;
            
            default: {
                return null;
            }
        }
        return goalObject;
    }

    /**
     * Recursively gets the and of two types of gaols
     * @param world the current world
     * @return boolean 
     */
    @Override
    public boolean metGoal(LoopManiaWorld world) {
        return goal1.metGoal(world) && goal2.metGoal(world);
    }


    
    
}
