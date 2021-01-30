package newlang5;

import java.util.LinkedList;
import java.util.List;

public class ExprListNode extends Node {
	List<Node> exprList;

	public ExprListNode(Environment env) {
		super(env);
		exprList = new LinkedList<Node>();
	}

	@Override
	public void parse() throws Exception {
		while (true) {
			Node elm = peek_handle(Symbol.expr);
			if (elm != null) {
				exprList.add(elm);
			}
			if (see(LexicalType.COMMA)) {
				continue;
			}
			break;
		}
	}

	public List<Node> getElements() throws Exception { // debug
		return exprList;
	}
}
