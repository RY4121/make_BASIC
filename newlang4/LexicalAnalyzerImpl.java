package newlang4;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
	private Map<String, LexicalType> reserved;
	private PushbackReader pr;
	private Deque<LexicalUnit> peek_deque;

	public LexicalAnalyzerImpl(InputStream in) {
		pr = new PushbackReader(new InputStreamReader(in));
		reserved = new HashMap<>();
		for (LexicalType type : LexicalType.values()) {
			reserved.put(type.getNotation(), type);
		}
		peek_deque = new LinkedList<>();
	}

	public LexicalUnit feed() throws Exception {
		LexicalType type;
		while (true) {
			String var = "";
			boolean asSymbol = false;
			boolean doubleSymbol = false;
			int count = pr.read();
			char c;

			if (count < 0 || count == 65535) {
				peek_deque.add(new LexicalUnit(LexicalType.EOF));
				return new LexicalUnit(LexicalType.EOF);
			} else
				c = (char) count;
			if (isSpace(c))
				continue;
			if (c == '"')
				return handleLiteral();
			if (isSymbol(String.valueOf(c)))
				asSymbol = true;
			if (isContinuousSymbol(String.valueOf(c)))
				doubleSymbol = true;

			while (true) {
				var += c;
				c = (char) pr.read();

				if (c == '.') {
					LexicalUnit tempLU = handleDoubleNum();
					var += '.' + tempLU.getValue().getSValue();
					peek_deque.add(new LexicalUnit(tempLU.getType(), new ValueImpl(var)));
					return new LexicalUnit(tempLU.getType(), new ValueImpl(var));
				} else
					type = LexicalType.INTVAL;

				if (isSpace(c))
					break;
				if (asSymbol && isDigitOrAlpha(String.valueOf(c))) {
					pr.unread(c);
					break;
				}
				if (!isDigitOrAlpha(String.valueOf(c))) {
					if (doubleSymbol) {
						if (!isFourOperation(String.valueOf(c))) {
							var += c;
						} else {
							pr.unread(c);
						}
					} else
						pr.unread(c);
					break;
				}
			}

			if (isDigit(var)) {
				peek_deque.add(new LexicalUnit(type, new ValueImpl(var)));
				return new LexicalUnit(type, new ValueImpl(var));
			} else if (isSymbol(var)) {
				peek_deque.add(new LexicalUnit(reserved.get(var), new ValueImpl(var)));
				return new LexicalUnit(reserved.get(var), new ValueImpl(var));
			} else if (isVarName(var)) {
				peek_deque.add(new LexicalUnit(LexicalType.NAME, new ValueImpl(var)));
				return new LexicalUnit(LexicalType.NAME, new ValueImpl(var));
			} else
				continue;
		}
	}

	public LexicalUnit handleDoubleNum() throws IOException {
		String doubleVal = "";
		while (true) {
			int count = pr.read();
			if (count < 0) {
				pr.unread(count);
				break;
			}
			char c = (char) count;
			if (!isDigit(String.valueOf(c))) {
				pr.unread(count);
				break;
			}
			doubleVal += c;
		}

		return new LexicalUnit(LexicalType.DOUBLEVAL, new ValueImpl(doubleVal));
	}

	public LexicalUnit handleLiteral() throws IOException {
		String literal = "";
		while (true) {
			int count = pr.read();
			if (count < 0) {
				peek_deque.add(new LexicalUnit(LexicalType.EOF));
				return new LexicalUnit(LexicalType.EOF);
			}
			char c = (char) count;
			if (c == '"')
				break;
			literal += c;
		}

		peek_deque.add(new LexicalUnit(LexicalType.LITERAL, new ValueImpl(literal)));
		return new LexicalUnit(LexicalType.LITERAL, new ValueImpl(literal));
	}

	public boolean isVarName(String str) {
		return Pattern.compile("^[A-Za-z]+[A-Za-z0-9]*$").matcher(str).matches();
	}

	public boolean isDigitOrAlpha(String str) {
		return Pattern.compile("^[A-Za-z0-9]+$").matcher(str).matches();
	}

	public boolean isBreakLine(char ch) {
		if (ch == '\n')
			return true;
		return false;
	}

	public boolean isDigit(String str) {
		return Pattern.compile("^[0-9\\.]+$").matcher(str).matches();
	}

	private boolean isSymbol(String str) throws IOException {
		if (reserved.containsKey(str))
			return true;
		return false;
	}

	private boolean isContinuousSymbol(String str) {
		return Pattern.compile("^[<=>]+$").matcher(str).matches();
	}

	private boolean isFourOperation(String str) {
		return Pattern.compile("^[\\+\\-\\*\\/]+$").matcher(str).matches();
	}

	public boolean isSpace(char ch) {
		if (ch == ' ')
			return true;
		return false;
	}

	@Override
	public LexicalUnit get() throws Exception {
		if (peek_deque.peek() == null) {
			feed();
		}
		LexicalUnit lu = peek_deque.poll();
		return lu;
	}

	@Override
	public LexicalUnit peek() throws Exception {
		LexicalUnit lu = null;
		if (peek_deque.peek() == null) {
			lu = feed();
		} else {
			lu = peek_deque.peek();
		}
		return lu;
	}

	@Override
	public LexicalUnit peek2() throws Exception {
		LexicalUnit lu;
		if (peek_deque.peek() == null) {
			lu = feed();
			peek2();
		} else {
			LexicalUnit temp = peek_deque.poll();
			lu = peek();
			peek_deque.addFirst(temp);
		}
		return lu;
	}

}
