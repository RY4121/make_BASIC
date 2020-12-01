package newlang4;

public class ExpressionNode extends Node {
	Node elm;

	public ExpressionNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		int lp_cnt = 0;
		while (true) {
			LexicalType ft = peek().getType();

			if (ft == LexicalType.END) {
				break;
			} else if (see(LexicalType.SUB)) {
				continue;
			} else if (see(LexicalType.LP)) {
				lp_cnt++;
				continue;
			}

			elm = peek_handle(Symbol.constant);

			if (elm != null) {
				continue;
			}

			switch (ft) {
				case ADD:
				case SUB:
				case MUL:
				case DIV:
					expect(ft);
					continue;
				case RP:
					if (lp_cnt > 0) {
						lp_cnt--;
						expect(ft);
						continue;
					}
					break;
				default:
					break;
			}

			LexicalType ft2 = peek2().getType();
			if (ft2 == LexicalType.LP && ft == LexicalType.NAME) {
				elm = peek_handle(Symbol.call_func);
				continue;
			}

			elm = peek_handle(Symbol.var);
			if (elm != null) {
				continue;
			}

			break;
		}
	}

	@Override
	public String toString() {
		return "expr";
	}
}
