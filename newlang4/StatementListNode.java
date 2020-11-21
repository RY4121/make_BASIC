package newlang4;

// 完成
public class StatementListNode extends Node {

	@Override
	public void parse() throws Exception {
		while (true) {
			LexicalType ft = peek().getType();
			if (ft == LexicalType.END) break;
			Node elm = peek_handle(Symbol.stmt); // 次の字句が確定していない場合はpeek_handle()を用いる
			if (elm == null) { // Statementの開始ではなかった場合
				elm = peek_handle(Symbol.block);
			} else {
				//				sub_nodes.add(elm);
				continue;

			}

			if (see(LexicalType.NL)) continue;
			break; // stmtでもblockでもなければ終了
		}
	}
}
