package newlang4;

public class StatementListNode extends Node {

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
			} else {
				continue;
			}

			if (see(LexicalType.NL)) {
				continue;
			}

			break;
		}
	}
}
