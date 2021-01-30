package newlang5;

public class CondNode extends Node {
	Node elm1;
	Node elm2;
	LexicalType ft;

	public CondNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		elm1 = handle(Symbol.expr);
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

		elm2 = handle(Symbol.expr);
	}

	@Override
	public Value getValue() throws Exception {
		System.out.println("Cond#getValue()" + elm1.getClass());
		double lVal = elm1.getValue().getDValue();
		double rVal = elm2.getValue().getDValue();
		System.out.println("\tCond左辺\t" + elm1.getValue().getSValue());
		System.out.println("\tCond右辺\t" + elm2.getValue().getSValue());

		switch (ft) {
			case EQ:
				if (lVal == rVal) {
					return new ValueImpl(true);
				}
				return new ValueImpl(false);
			case GT:
				if (lVal > rVal) {
					return new ValueImpl(true);
				}
				return new ValueImpl(false);
			case LT:
				if (lVal < rVal) {
					return new ValueImpl(true);
				}
				return new ValueImpl(false);
			case GE:
				if (lVal >= rVal) {
					return new ValueImpl(true);
				}
				return new ValueImpl(false);
			case LE:
				if (lVal <= rVal) {
					return new ValueImpl(true);
				}
				return new ValueImpl(false);
			case NE:
				if (lVal != rVal) {
					return new ValueImpl(true);
				}
				return new ValueImpl(false);
			default:
				return null;
		}
	}
}
