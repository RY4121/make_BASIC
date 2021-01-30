package newlang5;

public class FunctionCallNode extends Node {
	ExprListNode args;
	Function function;
	String name;

	public FunctionCallNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		// expect(LexicalType.NAME);
		LexicalUnit f = get();
		if (f.getType() != LexicalType.NAME) {
			error();
		}
		name = f.getValue().getSValue();
		function = env.getFunction(name);
		expect(LexicalType.LP);
		args = (ExprListNode) handle(Symbol.expr_list);
		expect(LexicalType.RP);
	}

	@Override
	public Value getValue() throws Exception {
		return function.invoke(args);
	}
}
