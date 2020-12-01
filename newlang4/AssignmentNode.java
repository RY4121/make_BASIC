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
		body = handle(Symbol.expr_list);
	}

	@Override
	public String toString() {
		return String.format("[%s]", body.toString());
	}
}
