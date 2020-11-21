package newlang4;

// 最も巨大化するクラス
public class ExpressionNode extends Node {
	Node body;

	public ExpressionNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		body = handle(Symbol.expr_list);
	}
}
