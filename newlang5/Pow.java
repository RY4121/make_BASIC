package newlang5;

import java.util.List;

public class Pow extends Function {

    @Override
    public Value invoke(ExprListNode arg) throws Exception {
        List<Node> args = arg.getElements();
        double result = Math.pow(args.get(0).getValue().getDValue(), args.get(1).getValue().getDValue());
        return new ValueImpl(result);
    }
}
