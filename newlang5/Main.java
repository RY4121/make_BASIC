package newlang5;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("test4.bas");
		LexicalAnalyzer lex = new LexicalAnalyzerImpl(in);
		Environment env = new Environment(lex);

		// while (true) {
		// LexicalUnit unit = lex.get();
		// System.out.println(unit);
		// if (unit.getType() == LexicalType.EOF)
		// break;
		// }

		Node p = Symbol.program.handle(env);
		// System.out.println(p);
		// System.out.println(p.polish_list);
		System.out.println("字句解析終了");
		System.out.println(p.getValue());
		// try {
		// System.out.println(p.getValue().getSValue());
		// } catch (Exception e) {
		// System.out.println(e);
		// System.out.println("end of program");
		// }
	}
}
