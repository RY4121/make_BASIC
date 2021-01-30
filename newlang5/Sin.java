package newlang5;

import java.util.List;

public class Sin extends Function {
    @Override
    public Value invoke(ExprListNode arg) throws Exception {
        List<Node> args = arg.getElements();
        double result = Math.sin(args.get(0).getValue().getDValue());
        return new ValueImpl(result);
    }
}
