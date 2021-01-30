package newlang5;

public class BlockNode extends Node {
	Node body;

	public BlockNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		Node elm = peek_handle(Symbol.loop);
		if (elm != null) {
			body = elm;
			return;
		}
		body = handle(Symbol.if_prefix);
	}

	@Override
	public Value getValue() throws Exception {
		return body.getValue();
	}
}
