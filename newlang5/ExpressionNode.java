package newlang5;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ExpressionNode extends Node {
	Node body;
	boolean biF = false;
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
			Node elm;
			switch (ft2) {
				case ADD:
				case SUB:
				case MUL:
				case DIV:
					elm = peek_handle(Symbol.binaryOp);
					if (elm != null) {
						biF = true;
					}
					break;
				default:
					break;
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
				body = elm;
				stack.addFirst(sValue);
				continue;
			}

			if (funcFlg) {
				ft = peek().getType();
				String tempStr = stack.poll();
				stack.addFirst("(");
				stack.addFirst(tempStr);
				op_stack.addFirst("(");
				funcFlg = false;
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
					break;
			}

			if (ft2 == LexicalType.LP && ft == LexicalType.NAME) {
				funcFlg = true;
				elm = peek_handle(Symbol.call_func);
				if (elm != null) {
					body = elm;
				}
				continue;
			}

			elm = peek_handle(Symbol.var);
			if (elm != null) {
				body = elm;
				stack.addFirst(sValue);
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
			return;
		}
		if (op_map.get(sValue) >= op_map.get(op_stack.peek())) {
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

	@Override
	public Value getValue() throws Exception {
		System.out.println("Expr#getValue()\t" + biF);
		if (!biF) {
			return body.getValue();
		}

		ArrayDeque<String> result = new ArrayDeque<>();
		boolean delOk = true;
		int cnt = 0;

		for (ArrayDeque ad : polish_list) {
			System.out.println("\t\t+Ex#ad\t" + ad + polish_list);
			cnt++;
			ArrayDeque<String> temp = new ArrayDeque<>();

			temp = ad.clone();

			while (true) {
				String item = (String) temp.poll();
				if (item == null) {
					break;
				}
				if (isFourOperation(item)) {
					delOk = false;
					break;
				}
			}
			if (delOk) {
				System.out.println("\t\t\titem\t消しました" + cnt);
				polish_list.remove(--cnt);
				System.out.println("\t\t\t" + "DEBUG" + polish_list);
				// continue;
			}

			temp = ad.clone();
			System.out.println("\tExpr#polish" + polish_list + "\t" + temp + "\t" + delOk + "\t" + cnt);
			while (temp.peek() != null) {
				String item = (String) temp.poll();
				try {
					System.out.println("\titemは大丈夫です" + item);
					item = env.getVariable(item).getValue().getSValue();

				} catch (Exception e) {
					// do nothing
				}
				System.out.println("\tExpr#item\t" + item);
				if (!isFourOperation(item) && !isNumber(item)) {
					return new ValueImpl(item);
				}
				if (!isFourOperation(item)) {
					result.addFirst(item);
					continue;
				}
				System.out.println("\tExpr#result\t" + result);
				String arg1 = (String) result.poll();
				String arg2 = (String) result.poll();
				double d2 = 0.0;
				double d1 = 0.0;
				int i2 = 0;
				int i1 = 0;
				boolean isDouble = false;
				if (arg1.contains(".") || arg2.contains(".")) {
					d2 = Double.parseDouble(arg1);
					d1 = Double.parseDouble(arg2);
					isDouble = true;
				} else {
					i2 = Integer.parseInt(arg1);
					i1 = Integer.parseInt(arg2);
				}

				switch (item) {
					case "+":
						if (isDouble) {
							result.addFirst(String.valueOf(d1 + d2));
						} else {
							result.addFirst(String.valueOf(i1 + i2));
						}
						break;
					case "-":
						if (isDouble) {
							result.addFirst(String.valueOf(d1 - d2));
						} else {
							result.addFirst(String.valueOf(i1 - i2));
						}
						break;
					case "*":
						if (isDouble) {
							result.addFirst(String.valueOf(d1 * d2));
						} else {
							result.addFirst(String.valueOf(i1 * i2));
						}
						break;
					case "/":
						if (isDouble) {
							result.addFirst(String.valueOf(d1 / d2));
						} else {
							result.addFirst(String.valueOf(i1 / i2));
						}
						break;
					default:
						break;
				}
			}
		}
		return new ValueImpl(result.poll());
	}

	private boolean isFourOperation(String str) {
		return Pattern.compile("^[\\+\\-\\*\\/]+$").matcher(str).matches();
	}

	private boolean isNumber(String str) {
		return Pattern.compile("^[0-9\\.]+$").matcher(str).matches();
	}
}