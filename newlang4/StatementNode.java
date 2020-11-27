package newlang4;

// 実装が必要
public class StatementNode extends Node {
	private Node body;

	public StatementNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("\tcall StatementNode#parse()");

		LexicalType ft = peek().getType();
		System.out.println("SN#parse()#ft" + ft + "\t" + peek());
		if (ft == LexicalType.END) {
			return;
		} else if (ft == LexicalType.FOR) {
			expect(LexicalType.FOR);
			body = handle(Symbol.subst);
			expect(LexicalType.TO);
			expect(LexicalType.INTVAL);
			expect(LexicalType.NL);
			body = handle(Symbol.stmt_list);
			expect(LexicalType.NL);
			expect(LexicalType.NEXT);
			expect(LexicalType.NAME);
		}

		ft = peek2().getType();
		System.out.println("SN#\t" + ft);
		if (ft == LexicalType.EQ) {
			body = handle(Symbol.subst); // 代入文確定
			sub_nodes.add(body);
		} else if (ft == LexicalType.LP) {
			body = handle(Symbol.call_func);
			sub_nodes.add(body);
		} else {
			return;
		}

//		System.exit(0);
			//			<subst> | <call_func> | <FOR> <subst> <TO> <INTVAL> <NL> <stmt_list> <NEXT> <NAME> | <END>
	}
}
