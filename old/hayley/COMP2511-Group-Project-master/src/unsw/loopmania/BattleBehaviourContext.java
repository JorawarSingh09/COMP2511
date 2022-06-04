package unsw.loopmania;

import java.util.List;

public interface BattleBehaviourContext {
    public List<Frame>  battleBehaviour(List<BasicEnemy> enemies, BasicEnemy boss, Character character, int baseCharacterHealth);
}
