package newlang4;

public class ConstantNode extends Node {
	Value value;

	public ConstantNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call ConstantNode#parse()");
		LexicalUnit elm = get();
		switch (elm.getType()) {
		case INTVAL:
		case DOUBLEVAL:
		case LITERAL:
			value = elm.getValue();
			System.out.println("\telm\t" + elm);
			System.out.println("\t\tCons終わり");
			return;
		default:
			error("syntax error");
		}
	}

	@Override
	public String toString() {
		return this.value.getSValue();
	}
}
