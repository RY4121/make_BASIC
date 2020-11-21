package newlang4;

// 完成
public class ProgramNode extends Node {
	Node body;

	public ProgramNode(Environment env) {
		super(env);
	}

	@Override
	public void parse( ) throws Exception {
		body = handle(Symbol.stmt_list);
	}

	@Override
	public String toString() {
		return String.format("[%s]", body.toString());
	}
}
