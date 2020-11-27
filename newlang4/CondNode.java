package newlang4;

public class CondNode extends Node {
	Node body;

	public CondNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call CondNode#parse()");
		body = handle(Symbol.expr);
		LexicalType ft = peek().getType();
		switch (ft) {
			case EQ:
			case GT:
			case LT:
			case GE:
			case LE:
			case NE:
				System.out.println(get());
				System.out.println("HERE2.1\t");
				System.out.println(peek().getType());
				System.out.println("CN#switch_fin");
				break;
			default:
				error();
		}

		body = handle(Symbol.expr);
	}
}
