package newlang5;

public class LoopNode extends Node {
	Node elm;
	Node stmtBody;

	public LoopNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		if (see(LexicalType.WHILE)) {
			elm = handle(Symbol.cond);
			expect(LexicalType.NL);
			stmtBody = handle(Symbol.stmt_list);
			expect(LexicalType.WEND);
			expect(LexicalType.NL);
			return;
		}
		expect(LexicalType.DO);
		LexicalType ft = peek().getType();
		switch (ft) {
			case UNTIL:
				// <cond> <NL> <stmt_list> <LOOP> <NL>
				expect(LexicalType.UNTIL);
				elm = handle(Symbol.cond);
				expect(LexicalType.NL);
				stmtBody = handle(Symbol.stmt_list);
				expect(LexicalType.LOOP);
				expect(LexicalType.NL);
				return;
			case WHILE:
				// <cond> <NL> <stmt_list> <LOOP> <NL>
				expect(LexicalType.WHILE);
				handle(Symbol.cond);
				expect(LexicalType.NL);
				handle(Symbol.stmt_list);
				expect(LexicalType.LOOP);
				expect(LexicalType.NL);
				return;
			case NL:
				// <stmt_list> <LOOP> (<WHILE>|<UNTIL>) <cond> <NL>
				expect(LexicalType.NL);
				handle(Symbol.stmt_list);
				expect(LexicalType.LOOP);
				if (!see(LexicalType.WHILE)) {
					expect(LexicalType.UNTIL);
				}
				handle(Symbol.cond);
				expect(LexicalType.NL);
				return;
			default:
				error("syntax error");
		}
	}

	@Override
	public Value getValue() throws Exception {
		System.out.println("Loop#getValue()");
		while (!elm.getValue().getBValue()) {
			stmtBody.getValue();
		}
		return null;
	}
}
