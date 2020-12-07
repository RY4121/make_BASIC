package newlang4;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("temp.bas");
		LexicalAnalyzer lex = new LexicalAnalyzerImpl(in);
		Environment env = new Environment(lex);

		Node p = Symbol.program.handle(env);
		System.out.println(p);
		// BinaryOperatorNode bp = new BinaryOperatorNode(env);
		// while (bp.op_stack.peek() != null) {
		// bp.stack.addFirst(bp.op_stack.poll());
		// }
		System.out.println("HERE");
		System.out.println(p.bQueue);
	}
}
