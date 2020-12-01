package newlang4;

public class VariableNode extends Node {
	Value value;

	public VariableNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		LexicalUnit elm = get();
		value = elm.getValue();
		return;
	}

	@Override
	public String toString() {
		return value.getSValue();
	}
}
