package newlang4;

public class AssignmentNode extends Node {
	Node body;

	public AssignmentNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		body = handle(Symbol.leftvar);
		expect(LexicalType.EQ);
		assFlg = true;
		body = handle(Symbol.expr);
	}
}
