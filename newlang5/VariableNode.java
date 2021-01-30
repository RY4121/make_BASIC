package newlang5;

public class VariableNode extends Node {
	Variable value;

	public VariableNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		LexicalUnit elm = get();
		value = env.getVariable(elm.getValue().getSValue());
		return;
	}

	@Override
	public Value getValue() {
		return value.getValue();
	}

	public void setValue(Value value) {
		this.value.setValue(value);
	}
}
