package newlang4;

public class LoopNode extends Node {
	Node body;
	Value value;

	public LoopNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		System.out.println("call LoopNode#parse()" + peek() + peek2());
		if (!see(LexicalType.DO)) {
			return;
		}
		LexicalType ft = peek().getType();
		switch (ft) {
		case UNTIL:
//			<cond> <NL> <stmt_list> <LOOP> <NL>
			System.out.println("UNTILに入りました");
			expect(LexicalType.UNTIL);
			body = handle(Symbol.cond);
			System.out.println("LN#UNTIL\t" + peek().getType());
			expect(LexicalType.NL);
			System.out.println("改行終わった");
			body = handle(Symbol.stmt_list);
			System.out.println("ステートメント終わった");
			expect(LexicalType.LOOP);
			expect(LexicalType.NL);
			return;
		case WHILE:
//			<cond> <NL> <stmt_list> <LOOP> <NL>
			expect(LexicalType.WHILE);
			body = handle(Symbol.cond);
			expect(LexicalType.NL);
			body = handle(Symbol.stmt_list);
			expect(LexicalType.LOOP);
			expect(LexicalType.NL);
			return;
		case NL:
//			<stmt_list> <LOOP> <WHILE> <cond> <NL>
			body = handle(Symbol.stmt_list);
			expect(LexicalType.LOOP);
			expect(LexicalType.WHILE);
			body = handle(Symbol.cond);
			expect(LexicalType.NL);
			return;
		default:
			error("syntax error");
		}
		System.exit(0);
	}
}
