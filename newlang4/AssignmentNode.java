package newlang4;

public class AssignmentNode extends Node {

	public AssignmentNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		handle(Symbol.leftvar);
		expect(LexicalType.EQ);
		assFlg = true;
		handle(Symbol.expr);
	}
}
