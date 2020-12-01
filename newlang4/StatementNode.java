package newlang4;

public class StatementNode extends Node {
	Node body;

	public StatementNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		LexicalType ft = peek().getType();
		if (ft == LexicalType.END) {
			return;
		} else if (ft == LexicalType.FOR) {
			expect(LexicalType.FOR);
			body = handle(Symbol.subst);
			expect(LexicalType.TO);
			expect(LexicalType.INTVAL);
			expect(LexicalType.NL);
			body = handle(Symbol.stmt_list);
			// expect(LexicalType.NL);
			expect(LexicalType.NEXT);
			expect(LexicalType.NAME);
			return;
		}

		ft = peek2().getType();
		if (ft == LexicalType.EQ) {
			body = handle(Symbol.subst);
		} else if (ft == LexicalType.LP) {
			body = handle(Symbol.call_func);
		}
	}

	@Override
	public String toString() {
		return String.format("[%s]", body.toString());
	}
}
