package bool;

public class BooleanEvaluator {

    public static boolean evaluate(BooleanNode expression) {
        return expression.evaluate();
    }

    public static String prettyPrint(BooleanNode expression) {
        return expression.prettyPrint();
    }

    public static void main(String[] args) {
        BooleanLeaf newLeafTrue = new BooleanLeaf(true);
        BooleanLeaf newLeafFalse = new BooleanLeaf(false);
        BooleanLeaf newLeafTrue2 = new BooleanLeaf(true);
        BooleanLeaf newLeafFalse2 = new BooleanLeaf(false);
        BooleanLeaf newLeafTrue3 = new BooleanLeaf(true);

        BooleanCompositeAND newComposite1 = new BooleanCompositeAND();
        BooleanCompositeOR newComposite2 = new BooleanCompositeOR();
        BooleanCompositeNOT newComposite3 = new BooleanCompositeNOT();
        BooleanCompositeAND newCompositeBoth = new BooleanCompositeAND();
        BooleanCompositeOR newCompositeAll = new BooleanCompositeOR();

        newComposite1.add(newLeafTrue);
        newComposite1.add(newLeafFalse);
        newComposite2.add(newLeafTrue2);
        newComposite2.add(newLeafFalse2);
        newComposite3.add(newLeafTrue3);


        newCompositeBoth.add(newComposite1);
        newCompositeBoth.add(newComposite2);
        newCompositeAll.add(newCompositeBoth);
        newCompositeAll.add(newComposite3);

        Boolean result = BooleanEvaluator.evaluate(newCompositeAll);
        String prettyResult = BooleanEvaluator.prettyPrint(newCompositeAll);

        System.out.println(prettyResult + " = " + result);
        // BooleanEvaluator.evaluate(...)
        // BooleanEvaluator.evaluate(...)
    }

}