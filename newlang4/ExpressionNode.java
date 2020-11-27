package newlang4;

public class ExpressionNode extends Node {
	Node body;

	public ExpressionNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call ExpressionNode#parse()");
		// <expr> <ADD> <expr> | <expr> <SUB> <expr> | <expr> <MUL> <expr> | <expr><DIV> <expr> |
		// <SUB> <expr> | <LP> <expr> <RP> |
		// <var> | <INTVAL> | <DOUBLEVAL> | <LITERAL> | <call_func>
		while (true) {
			LexicalType ft = peek().getType();
			System.out.println("\tEN#ft\t" + peek());
			if (ft == LexicalType.END) {
				break;
			} else if (see(LexicalType.SUB)) {
				continue;
			} else if (see(LexicalType.LP)) {
				continue;
			}

			Node elm = peek_handle(Symbol.constant);

			if (elm != null)
				continue;
//			if (see(LexicalType.NL))
//				continue;
			if (see(LexicalType.RP))
				continue;

			elm = peek_handle(Symbol.var);
			if (elm == null) {
				elm = peek_handle(Symbol.call_func);
			} else {
				sub_nodes.add(elm);
				continue;
			}

			switch (ft) {
				case ADD:
				case SUB:
				case MUL:
				case DIV:
					 System.out.println("EN#演算子" + ft);
					see(ft);
					continue;
				default:
					System.out.println("\tEN#default#\t" + ft);
					break;
				// error();
			}

			break;
		}
	}

	@Override
	public String toString() {
		return body.toString();
	}
}
