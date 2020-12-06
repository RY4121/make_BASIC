package newlang4;

import java.util.ArrayDeque;
import java.util.Deque;

public class BinaryOperatorNode extends Node {
    Deque<Node> stack = new ArrayDeque<>();

    public BinaryOperatorNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        System.out.println("BinaryOperatorNode.java");
        // Node elm = peek_handle(Symbol.constant);
        // if (elm == null) {
        // elm = handle(Symbol.var);
        // stack.add(elm);
        // }

    }
}
