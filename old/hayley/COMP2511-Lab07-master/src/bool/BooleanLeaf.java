package bool;

import java.util.List;
import java.util.ArrayList;

public class BooleanLeaf extends BooleanNode {
    private Boolean bool;

    public BooleanLeaf(Boolean bool) {
        super();
        this.bool = bool;
    }

    public boolean evaluate() {
        return bool;
    }

    public void add(BooleanNode node) {
    }

    public void remove(BooleanNode node) {
    }

    public String prettyPrint() {
        return bool.toString();
    }

    public List<BooleanNode> getChildren() {
        return new ArrayList<BooleanNode>();
    }
}
