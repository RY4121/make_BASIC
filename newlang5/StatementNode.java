package newlang5;

public class StatementNode extends Node {

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
			handle(Symbol.subst);
			expect(LexicalType.TO);
			expect(LexicalType.INTVAL);
			expect(LexicalType.NL);
			handle(Symbol.stmt_list);
			expect(LexicalType.NEXT);
			expect(LexicalType.NAME);
			return;
		}

		ft = peek2().getType();
		if (ft == LexicalType.EQ) {
			handle(Symbol.subst);
		} else if (ft == LexicalType.LP) {
			handle(Symbol.call_func);
		}
	}
}
