package newlang4;

public class AssignmentNode extends Node {
	private Node body;

	public AssignmentNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception{
		System.out.println("call AssignmentNode#parse()");
		body = handle(Symbol.leftvar);
		sub_nodes.add(body);
		expect(LexicalType.EQ);
		body = handle(Symbol.expr_list);
		sub_nodes.add(body);
	}
}
