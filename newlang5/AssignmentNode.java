package newlang5;

public class AssignmentNode extends Node {
    Node left;
    Node right;

    public AssignmentNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        left = handle(Symbol.leftvar);
        expect(LexicalType.EQ);
        assFlg = true;
        right = handle(Symbol.expr);
    }

    @Override
    public Value getValue() throws Exception {
        System.out.println("\t\tAss#getValue()\t");
        ((VariableNode) left).setValue(right.getValue());
        return null;
    }
}