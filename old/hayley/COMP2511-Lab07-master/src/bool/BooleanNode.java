package bool;

import java.util.List;

/**
 * A boolean BooleanNode.
 * @author Nick Patrikeos + @your name
 * 
 * Feel free to change this to an abstract class/interface as you see fit.
 */
public abstract class BooleanNode {
    public abstract boolean evaluate();
    public abstract void add(BooleanNode node);
    public abstract void remove(BooleanNode node);
    public abstract List<BooleanNode> getChildren();
    public abstract String prettyPrint();
}