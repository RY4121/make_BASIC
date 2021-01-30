package newlang5;

import java.util.LinkedList;
import java.util.List;

public class StatementListNode extends Node {
	List<Node> body = new LinkedList<>();

	public StatementListNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		while (true) {
			LexicalType ft = peek().getType();
			if (ft == LexicalType.END) {
				break;
			}

			Node elm = peek_handle(Symbol.stmt);

			if (elm == null) {
				elm = peek_handle(Symbol.block);
				if (elm != null) {
					body.add(elm);
				}
			} else {
				body.add(elm);
				continue;
			}

			if (see(LexicalType.NL)) {
				continue;
			}

			break;
		}
	}

	@Override
	public Value getValue() throws Exception {
		for (Node n : body) {
			n.getValue();
		}
		return null;
	}
}
