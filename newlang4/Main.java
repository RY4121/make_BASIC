package newlang4;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("test1.bas");
		newlang4.LexicalAnalyzer lex = new newlang4.LexicalAnalyzerImpl(in);
		Environment env = new Environment(lex);

		Node p = Symbol.program.handle(env);
		System.out.println(p);
	}

}
