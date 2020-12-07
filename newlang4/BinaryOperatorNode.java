package newlang4;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class BinaryOperatorNode extends Node {
    static Deque<String> stack = new ArrayDeque<>();
    static Deque<String> op_stack = new ArrayDeque<>();
    static boolean opFlg = false;

    public BinaryOperatorNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        Map<String, Integer> op_map = new HashMap<String, Integer>();
        op_map.put("(", 0);
        op_map.put(")", 0);
        op_map.put("+", 1);
        op_map.put("-", 1);
        op_map.put("*", 2);
        op_map.put("/", 2);

        // System.out.println("BinaryOperatorNode.java\t" + peek());
        String sValue = peek().getValue().getSValue();
        LexicalType ft = peek().getType();
        LexicalType ft2 = peek2().getType();
        if (ft2 == LexicalType.ADD || ft2 == LexicalType.MUL) {
            terminal.add(peek2());
        }
        if (funcFlg) {
            System.out.println("\t\t\t入りました");
            ft = peek().getType();
            String t = stack.poll();
            stack.addFirst("(");
            stack.addFirst(t);
            op_stack.addFirst("(");
            funcFlg = false;
        }
        System.out.println("stack\t" + ft + "\t" + String.valueOf(funcFlg));
        switch (ft) {
            case LP:
                op_stack.addFirst(sValue);
                break;
            case RP:
                while (!op_stack.peek().equals("(")) {
                    stack.addFirst(op_stack.poll());
                    if (op_stack.peek() == null) {
                        break;
                    }
                }
                op_stack.poll();
                break;
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                // terminal.add(peek());
                if (op_stack.peek() == null) {
                    op_stack.addFirst(sValue);
                    break;
                }
                if (op_map.get(sValue) > op_map.get(op_stack.peek())) {
                    System.out.println("優先度が高いです");
                    op_stack.addFirst(sValue);
                } else {
                    while (op_map.get(sValue) <= op_map.get(op_stack.peek())) {
                        System.out.println("優先度が低いです");
                        stack.addFirst(op_stack.poll());
                        if (op_stack.peek() == null) {
                            break;
                        }
                    }
                    op_stack.addFirst(sValue);
                }
                break;
            default:
                stack.addFirst(sValue);
                break;
        }

        // System.out.println(stack);
        // System.out.println(op_stack);
        return;
    }
}
