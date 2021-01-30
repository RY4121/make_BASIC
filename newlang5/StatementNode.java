package newlang5;

import java.util.LinkedList;
import java.util.List;

public class StatementNode extends Node {
	List<Node> bodyList;
	Node body;

	public StatementNode(Environment env) {
		super(env);
		bodyList = new LinkedList<>();
	}

	@Override
	public void parse() throws Exception {
		LexicalType ft = peek().getType();
		if (ft == LexicalType.END) {
			return;
		} else if (ft == LexicalType.FOR) {
			expect(LexicalType.FOR);
			body = handle(Symbol.subst);
			bodyList.add(body);
			expect(LexicalType.TO);
			expect(LexicalType.INTVAL);
			expect(LexicalType.NL);
			body = handle(Symbol.stmt_list);
			bodyList.add(body);
			expect(LexicalType.NEXT);
			expect(LexicalType.NAME);
			return;
		}

		ft = peek2().getType();
		if (ft == LexicalType.EQ) {
			body = handle(Symbol.subst);
			bodyList.add(body);
		} else if (ft == LexicalType.LP) {
			body = handle(Symbol.call_func);
			bodyList.add(body);
		}
	}

	@Override
	public Value getValue() throws Exception {
		for (Node p : bodyList) {
			p.getValue();
		}
		return null;
	}
}
