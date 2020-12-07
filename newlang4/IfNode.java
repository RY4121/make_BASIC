package newlang4;

public class IfNode extends Node {
	Node body;

	public IfNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		expect(LexicalType.IF);
		body = handle(Symbol.cond);
		expect(LexicalType.THEN);
		Node elm = peek_handle(Symbol.stmt);
		if (elm == null) {
			// pattern3 or 4 or 5 or 6
			expect(LexicalType.NL);
			handle(Symbol.stmt_list);

			if (see(LexicalType.ELSE)) { // pattern5
				expect(LexicalType.NL);
				body = handle(Symbol.stmt_list);
			} else if (see(LexicalType.ELSEIF)) {
				// pattern4 or 6
				handle(Symbol.cond);
				expect(LexicalType.THEN);
				expect(LexicalType.NL);
				body = handle(Symbol.stmt_list);

				if (see(LexicalType.ELSE)) { // pattern6
					expect(LexicalType.NL);
					body = handle(Symbol.stmt_list);
				}
			}
			expect(LexicalType.ENDIF);
		} else {
			// pattern1 or 2
			if (see(LexicalType.ELSE)) { // pattern2
				body = handle(Symbol.stmt);
			}
			expect(LexicalType.NL);
		}
	}
}
