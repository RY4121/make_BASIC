package newlang4;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
	Environment env;
	protected Queue<Node> sub_Nodes;
	protected Queue<LexicalUnit> terminal;

	public Node(Environment env) {
		this.env = env;
		sub_Nodes = new LinkedList<>();
		terminal = new LinkedList<>();
	}

	public void parse() throws Exception {
	}

	public Value getValue() throws Exception {
		return null;
	}

	protected LexicalUnit get() throws Exception {
		LexicalUnit lu = env.getInput().get();
		terminal.add(lu);
		return lu;
	}

	protected LexicalUnit peek() throws Exception {
		return env.getInput().peek();
	}

	protected LexicalUnit peek2() throws Exception {
		return env.getInput().peek2();
	}

	protected Node handle(Symbol symbol) throws Exception {
		LexicalUnit f = peek();
		if (symbol.isFirst(f.getType())) {
			Node body = symbol.handle(env);
			sub_Nodes.add(body);
			return body;
		}
		error();
		return null;
	}

	protected Node peek_handle(Symbol symbol) throws Exception {
		LexicalUnit f = peek();
		if (symbol.isFirst(f.getType())) {
			Node body = symbol.handle(env);
			sub_Nodes.add(body);
			return body;
		}
		return null;
	}

	protected boolean see(LexicalType t) throws Exception {
		if (peek().getType() == t) {
			get();
			return true;
		} else
			return false;
	}

	protected void expect(LexicalType t) throws Exception {
		if (get().getType() != t)
			error();
	}

	protected void check(LexicalType t) throws Exception {
		if (peek().getType() != t)
			error();
	}

	static void error() throws Exception {
		throw new Exception("syntax error");
	}

	static void error(String mes) throws Exception {
		throw new Exception(mes);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		return showResults(sb, 0).toString();
	}

	private StringBuilder showResults(StringBuilder sb, int space) {
		for (Node node : sub_Nodes) {
			sb.append("\t".repeat(space)).append(node.getClass().getSimpleName()).append("\n");
			sb.append("\t".repeat(space)).append(node.terminal).append("\n");
			node.showResults(sb, ++space);
		}
		return sb;
		// while (sub_Nodes.peek() != null) {
		// Node e = sub_Nodes.poll();
		// sb.append("[");
		// sb.append(e.getClass().getSimpleName() + "," + e);
		// sb.append("]\n");
		// }
		// return sb.substring(0);
	}
}
