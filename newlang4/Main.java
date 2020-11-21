package newlang4;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("test1.bas");
		LexicalAnalyzer lex = new LexicalAnalyzerImpl(in);
		Environment env = new Environment(lex);
		Node p = Symbol.program.handle(env); // 構文解析を実行
//		System.out.println(p);
	}

}
