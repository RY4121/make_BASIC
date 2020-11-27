package newlang4;

public class BlockNode extends Node {
	Node body;

	public BlockNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call BlockNode#parse()\t" + peek());
		body = peek_handle(Symbol.loop);
		if (body != null) {
			return;
		}
		expect(LexicalType.IF);
		body = handle(Symbol.if_prefix);
	}

	@Override
	public String toString() {
		return body.toString();
	}

}
