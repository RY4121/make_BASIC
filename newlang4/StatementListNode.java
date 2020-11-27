package newlang4;

// 完成
public class StatementListNode extends Node {
	Node body;

	public StatementListNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call StatementListNode#parse()");
		while (true) {
			LexicalType ft = peek().getType();
			if (ft == LexicalType.END) {
				break;
			}

			Node elm = peek_handle(Symbol.stmt);

			if (elm == null) {
				elm = peek_handle(Symbol.block);
				if (elm != null) {
					//					sub_nodes.add(e)
				}
			} else {
				sub_nodes.add(elm);
				continue;
			}

			if (see(LexicalType.NL)) {
				continue;
			}

			break;
		}
	}
}
