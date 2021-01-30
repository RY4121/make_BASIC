package newlang5;

public class LoopNode extends Node {
	Node elm;
	Node stmtBody;
	String loopType;

	public LoopNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		if (see(LexicalType.WHILE)) {
			loopType = "WHILE";
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
				loopType = "UNTIL";
				expect(LexicalType.UNTIL);
				elm = handle(Symbol.cond);
				expect(LexicalType.NL);
				stmtBody = handle(Symbol.stmt_list);
				expect(LexicalType.LOOP);
				expect(LexicalType.NL);
				return;
			case WHILE:
				// <cond> <NL> <stmt_list> <LOOP> <NL>
				loopType = "DOWHILE";
				expect(LexicalType.WHILE);
				elm = handle(Symbol.cond);
				expect(LexicalType.NL);
				stmtBody = handle(Symbol.stmt_list);
				expect(LexicalType.LOOP);
				expect(LexicalType.NL);
				return;
			case NL:
				// <stmt_list> <LOOP> (<WHILE>|<UNTIL>) <cond> <NL>
				loopType = "DO-WHILE";
				expect(LexicalType.NL);
				stmtBody = handle(Symbol.stmt_list);
				expect(LexicalType.LOOP);
				if (!see(LexicalType.WHILE)) {
					loopType = "DO-UNTIL";
					expect(LexicalType.UNTIL);
				}
				elm = handle(Symbol.cond);
				expect(LexicalType.NL);
				return;
			default:
				error("syntax error");
		}
	}

	@Override
	public Value getValue() throws Exception {
		switch (loopType) {
			case "WHILE":
			case "DOWHILE":
				while (elm.getValue().getBValue()) {
					stmtBody.getValue();
				}
				break;
			case "UNTIL":
				while (!elm.getValue().getBValue()) {
					stmtBody.getValue();
				}
				break;
			case "DO-WHILE":
				stmtBody.getValue();
				while (elm.getValue().getBValue()) {
					stmtBody.getValue();
				}
				break;
			case "DO-UNTIL":
				stmtBody.getValue();
				while (!elm.getValue().getBValue()) {
					stmtBody.getValue();
				}
				break;
			default:
				break;
		}
		return null;
	}
}
