package bool;

import java.util.List;
import java.util.ArrayList;


public class BooleanCompositeAND extends BooleanNode {
    private List<BooleanNode> children;

    public BooleanCompositeAND() {
        super();
        this.children = new ArrayList<BooleanNode>();
    }

    public boolean evaluate() {
        return children.get(0).evaluate() && children.get(1).evaluate();
    }

    public String prettyPrint() {
        return '(' + children.get(0).prettyPrint() + " AND " + children.get(1).prettyPrint() + ')';
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
