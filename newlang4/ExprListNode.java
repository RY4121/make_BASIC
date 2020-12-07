package newlang4;

public class ExprListNode extends Node {
	Node body;

	public ExprListNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		while (true) {
			Node elm = peek_handle(Symbol.expr);
			if (elm != null) {
				body = elm;
			}
			if (see(LexicalType.COMMA)) {
				continue;
			}
			break;
		}
	}
}
