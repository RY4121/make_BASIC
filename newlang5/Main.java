package newlang5;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("test4.bas");
		LexicalAnalyzer lex = new LexicalAnalyzerImpl(in);
		Environment env = new Environment(lex);

		Node p = Symbol.program.handle(env);
		try {
			p.getValue();
		} catch (Exception e) {
			System.out.println("syntax error");
			e.printStackTrace();
		}
		System.out.println("end of program");
	}
}
