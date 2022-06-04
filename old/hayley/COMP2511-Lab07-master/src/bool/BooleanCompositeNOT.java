package bool;

import java.util.List;
import java.util.ArrayList;


public class BooleanCompositeNOT extends BooleanNode {
    private List<BooleanNode> children;

    public BooleanCompositeNOT() {
        super();
        this.children = new ArrayList<BooleanNode>();
    }

    public boolean evaluate() {
        return ! children.get(0).evaluate();
    }

    public String prettyPrint() {
        return "(NOT " + children.get(0).prettyPrint() + ')';
    }

    public void add(BooleanNode node) {
        children.add(node);
    }

    public void remove(BooleanNode node) {
        children.remove(node);
    }

    public List<BooleanNode> getChildren() {
        return children;
    }
}
