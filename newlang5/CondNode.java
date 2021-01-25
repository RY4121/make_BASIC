package newlang5;

public class CondNode extends Node {

	public CondNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		handle(Symbol.expr);
		LexicalType ft = peek().getType();
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
}
