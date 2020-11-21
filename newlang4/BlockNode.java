package newlang4;

// 完成
public class BlockNode extends Node {
	Node body;

	public BlockNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		body = peek_handle(Symbol.loop);
		if (body != null) return;
		body = handle(Symbol.if_prefix);
	}

	@Override
	public String toString() {
		return body.toString();
	}

}
