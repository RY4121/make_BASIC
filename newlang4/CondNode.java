package newlang4;

public class CondNode extends Node {
	Node body;
	LexicalType ft;

	public CondNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		handle(Symbol.expr);
		ft = peek().getType();
		switch (ft) {
			case EQ:
			case GT:
			case LT:
			case GE:
			case LE:
			case NE:
				expect(ft);
				break;
			default:
				error();
		}

		handle(Symbol.expr);
	}

	@Override
	public String toString() {
		return ft.toString();
	}
}
