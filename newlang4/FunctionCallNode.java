package newlang4;

public class FunctionCallNode extends Node {
	Node body;

	public FunctionCallNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		expect(LexicalType.NAME);
		expect(LexicalType.LP);
		body = handle(Symbol.expr_list);
		expect(LexicalType.RP);
	}
}
