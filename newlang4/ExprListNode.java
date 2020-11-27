package newlang4;

// Exprの並び
public class ExprListNode extends Node {
	Node body;

	public ExprListNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call ExpressionNode#parse()");
		while (true) {

			body = peek_handle(Symbol.expr);

			if (see(LexicalType.COMMA)) {
				continue;
			}

			break;
		}
	}
}

