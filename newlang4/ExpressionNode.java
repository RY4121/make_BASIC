package newlang4;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class ExpressionNode extends Node {
	Node elm;
	private static ArrayDeque<String> stack = new ArrayDeque<>();
	private static ArrayDeque<String> op_stack = new ArrayDeque<>();
	private static boolean funcFlg = false;
	private Map<String, Integer> op_map;

	public ExpressionNode(Environment env) {
		super(env);
	}

	@Override
	public void parse() throws Exception {
		int lp_cnt = 0;

		op_map = new HashMap<String, Integer>();
		op_map.put("(", 0);
		op_map.put(")", 0);
		op_map.put("+", 1);
		op_map.put("-", 1);
		op_map.put("*", 2);
		op_map.put("/", 2);

		while (true) {
			LexicalType ft = peek().getType();
			LexicalType ft2 = peek2().getType();
			String sValue = peek().getValue().getSValue();

			switch (ft2) {
				case ADD:
				case SUB:
				case MUL:
				case DIV:
					peek_handle(Symbol.binaryOp);
					break;
				default:
					break;
			}
			if (funcFlg) {
				ft = peek().getType();
				String tempStr = stack.poll();
				stack.addFirst("(");
				stack.addFirst(tempStr);
				op_stack.addFirst("(");
				funcFlg = false;
			}

			if (ft == LexicalType.END) {
				break;
			} else if (see(LexicalType.SUB)) {
				handleFourSymbol(sValue);
				continue;
			} else if (see(LexicalType.LP)) {
				lp_cnt++;
				op_stack.addFirst(sValue);
				continue;
			} else if (ft == LexicalType.NL) {
				break;
			} else if (ft == LexicalType.COMMA) {
				return;
			}

			elm = peek_handle(Symbol.constant);

			if (elm != null) {
				stack.addFirst(sValue);
				continue;
			}

			switch (ft) {
				case ADD:
				case SUB:
				case MUL:
				case DIV:
					handleFourSymbol(sValue);
					expect(ft);
					continue;
				case RP:
					if (op_stack.isEmpty()) {
						break;
					}
					while (!op_stack.peek().equals("(")) {
						stack.addFirst(op_stack.poll());
						if (op_stack.peek() == null) {
							break;
						}
					}
					op_stack.poll();

					if (lp_cnt > 0) {
						lp_cnt--;
						expect(ft);
						continue;
					}
					break;
				default:
					stack.addFirst(sValue);
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

			break;
		}

		while (op_stack.peek() != null) {
			stack.addFirst(op_stack.poll());
		}
		ArrayDeque<String> _stack = new ArrayDeque<>();
		while (stack.peek() != null) {
			_stack.addLast(stack.pollLast());
		}
		if (!_stack.isEmpty() && assFlg) {
			polish_list.add(_stack);
		}
		assFlg = false;
		stack.clear();
	}

	private void handleFourSymbol(String sValue) {
		if (op_stack.peek() == null) {
			op_stack.addFirst(sValue);
		}
		if (op_map.get(sValue) > op_map.get(op_stack.peek())) {
			op_stack.addFirst(sValue);
		} else {
			while (op_map.get(sValue) < op_map.get(op_stack.peek())) {
				stack.addFirst(op_stack.poll());
				if (op_stack.peek() == null) {
					op_stack.addFirst(sValue);
					break;
				}
			}
		}
	}
}
