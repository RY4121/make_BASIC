package newlang5;

import java.util.LinkedList;
import java.util.List;

public class IfNode extends Node {
	List<Node> bodyList;
	Node body;

	public IfNode(Environment env) {
		super(env);
		bodyList = new LinkedList<>();
	}

	@Override
	public void parse() throws Exception {
		expect(LexicalType.IF);
		body = handle(Symbol.cond);
		bodyList.add(body);
		expect(LexicalType.THEN);
		Node elm = peek_handle(Symbol.stmt);
		if (elm == null) {
			// pattern3 or 4 or 5 or 6
			expect(LexicalType.NL);
			body = handle(Symbol.stmt_list);
			bodyList.add(body);

			if (see(LexicalType.ELSE)) { // pattern5
				expect(LexicalType.NL);
				body = handle(Symbol.stmt_list);
				bodyList.add(body);
			} else if (see(LexicalType.ELSEIF)) {
				// pattern4 or 6
				body = handle(Symbol.cond);
				bodyList.add(body);
				expect(LexicalType.THEN);
				expect(LexicalType.NL);
				body = handle(Symbol.stmt_list);
				bodyList.add(body);

				if (see(LexicalType.ELSE)) { // pattern6
					expect(LexicalType.NL);
					body = handle(Symbol.stmt_list);
					bodyList.add(body);
				}
			}
			expect(LexicalType.ENDIF);
		} else {
			bodyList.add(elm);
			// pattern1 or 2
			if (see(LexicalType.ELSE)) { // pattern2
				body = handle(Symbol.stmt);
				bodyList.add(body);
			}
			expect(LexicalType.NL);
		}
	}

	@Override
	public Value getValue() throws Exception {
		boolean condition = false;
		for (Node p : bodyList) {
			String pname = p.getClass().getSimpleName();
			if (pname.equals("CondNode")) {
				condition = p.getValue().getBValue();
			} else {
				if (!condition) {
					condition = true;
					continue;
				} else {
					condition = false;
					p.getValue();
				}
			}
		}
		return null;
	}
}
