package newlang4;

public class FunctionCallNode extends Node {

	public FunctionCallNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		expect(LexicalType.NAME);
		expect(LexicalType.LP);
		handle(Symbol.expr_list);
		expect(LexicalType.RP);
	}
}
