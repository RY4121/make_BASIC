package newlang4;

public class BinaryOperatorNode extends Node {

    public BinaryOperatorNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        terminal.add(peek2());
    }
}
