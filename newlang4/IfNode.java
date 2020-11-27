package newlang4;

public class IfNode extends Node {

	public IfNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call IfNode#parse()\t" + peek());
		if (see(LexicalType.IF)) {
			System.out.println("****************\t" + peek());
			return;
		}
		handle(Symbol.cond);
		expect(LexicalType.THEN);
//		expect(LexicalType.EQ);
		System.out.println("\t\tGGGGG");
		Node elm = peek_handle(Symbol.stmt);
		if (elm == null) {
			// pattern3
			// <if_prefix> <NL> <stmt_list> <else_block> <ENDIF> <NL>
			expect(LexicalType.NL);
			handle(Symbol.stmt_list);
			System.out.println("ここまではね");

			LexicalType ft = peek().getType();
			System.out.println("DEBUG\t" + ft);
			if(ft == LexicalType.ENDIF) {
				System.out.println("Pattern3");
			} else {


//			if (ft != null) {
				// pattern4 or 5 or 6
				/* Symbol.else_if_block */
				if (ft == LexicalType.ELSE) {
					// pattern5
					System.out.println("Pattern5");
					expect(LexicalType.ELSE);
					expect(LexicalType.NL);
					handle(Symbol.stmt_list);
				} else {
					// pattern4 or 6
					System.out.println("Pattern4 or 6");
					peek_handle(Symbol.else_if_prefix);
					expect(LexicalType.ELSEIF);
					handle(Symbol.cond);
					expect(LexicalType.THEN);
					expect(LexicalType.NL);
					handle(Symbol.stmt_list);

					ft = peek().getType();
					if (ft != LexicalType.ELSE) {
						System.out.println("PATTERN4");
						return;
					} else {
						System.out.println("PATTERN6");
						expect(LexicalType.ELSE);
						expect(LexicalType.NL);
						handle(Symbol.stmt_list);
					}
				}
			}
//			} else {
				// pattern3
//				System.out.println("Pattern3");
//			}
			/* END */
			expect(LexicalType.ENDIF);
			expect(LexicalType.NL);
		} else {
			// pattern1 or 2
			LexicalType ft = peek().getType();
			System.out.println("HERE" + peek());
//			expect(LexicalType.NL);

			if (see(LexicalType.NL)) {
				// pattern1
				System.out.println("PATTERN1");
				return;
			} else {
				// pattern2
				System.out.println("PATTERN2");
				expect(LexicalType.ELSE);
				handle(Symbol.stmt);
				expect(LexicalType.NL);
			}
		}

		// System.out.println("&&&\t" + ft);
	}
}
