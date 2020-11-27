package newlang4;

public class VariableNode extends Node {
	Value value;

	public VariableNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception{
		System.out.println("call Variable#parse()");
		LexicalUnit elm = get();
		System.out.println(elm);
		System.out.println("\t\t VN終わり");
		return;
	}
}
