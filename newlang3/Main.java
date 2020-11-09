package newlang3;

public class Main {

	public static void main(String[] args) throws Exception {
		Value value = new ValueImpl(-1.23);
		System.out.println(value.getIValue());
		System.out.println(value.getDValue());
		System.out.println(value.getSValue());
		System.out.println(value.getBValue());
		System.out.println(value.getType());

//		InputStream in = new FileInputStream("test1.bas");
//		LexicalAnalyzer lex = new LexicalAnalyzerImpl(in);
//		while (true) {
//			LexicalUnit unit = lex.get();
//			System.out.println(unit);
//			if (unit.getType() == LexicalType.EOF) break;
//		}
		System.out.println("that's all");
	}

}
