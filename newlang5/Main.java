package newlang5;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("temp.bas");
		LexicalAnalyzer lex = new LexicalAnalyzerImpl(in);
		Environment env = new Environment(lex);

		Node p = Symbol.program.handle(env);
		System.out.println(p);
		System.out.println(p.polish_list);
	}
}
