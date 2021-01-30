package newlang5;

import java.util.List;

public class PrintFunction extends Function {

    @Override
    public Value invoke(ExprListNode arg) throws Exception {
        List<Node> args = arg.getElements();
        String msg = "";
        for (Node n : args) {
            msg += n.getValue().getSValue();
        }
        System.out.println("\t\t\t\tPRINT出力\t" + msg);
        return null;
    }
}
