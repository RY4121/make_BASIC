package newlang4;

public class ExpressionNode extends Node {
	Node elm;

	public ExpressionNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		int lp_cnt = 0;
		while (true) {
			LexicalType ft = peek().getType();
			LexicalType ft2 = peek2().getType();
			System.out.println("EN\t" + peek());

			peek_handle(Symbol.binaryOp);

			if (ft == LexicalType.END) {
				break;
			} else if (see(LexicalType.SUB)) {
				continue;
			} else if (see(LexicalType.LP)) {
				lp_cnt++;
				continue;
			}

			elm = peek_handle(Symbol.constant);

			if (elm != null) {
				continue;
			}

			switch (ft) {
				case ADD:
				case SUB:
				case MUL:
				case DIV:
					expect(ft);
					continue;
				case RP:
					if (lp_cnt > 0) {
						lp_cnt--;
						expect(ft);
						continue;
					}
					break;
				default:
					break;
			}

			if (ft2 == LexicalType.LP && ft == LexicalType.NAME) {
				funcFlg = true;
				elm = peek_handle(Symbol.call_func);
				continue;
			}

			elm = peek_handle(Symbol.var);
			if (elm != null) {
				continue;
			}

			if (ft == LexicalType.COMMA) {
				return;
			}
			break;
		}

		System.out.println("\t\t\t入りました" + peek());
		while (BinaryOperatorNode.op_stack.peek() != null) {
			BinaryOperatorNode.stack.addFirst(BinaryOperatorNode.op_stack.poll());
		}
		// System.out.println("sayaka\t" + terminal);
		System.out.println("BON_op\t" + BinaryOperatorNode.op_stack);
		System.out.println("BON_stack\t" + BinaryOperatorNode.stack);
		bQueue.add(BinaryOperatorNode.stack);
		System.out.println(bQueue);
		// BinaryOperatorNode.stack.clear();
	}

	@Override
	public String toString() {
		return "expr";
	}
}
