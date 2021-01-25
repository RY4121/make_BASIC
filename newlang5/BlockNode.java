package newlang5;

public class BlockNode extends Node {
	Node body;

	public BlockNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		body = peek_handle(Symbol.loop);
		if (body != null) {
			return;
		}
		handle(Symbol.if_prefix);
	}
}
