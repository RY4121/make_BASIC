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
		System.out.println("\t\t\ttest\t" + value.var_name);
		return;
	}

	@Override
	public Value getValue() {
		System.out.println("\tVariable#getValue()");
		return value.getValue();
	}

	public void setValue(Value value) {
		this.value.setValue(value);
	}
}
