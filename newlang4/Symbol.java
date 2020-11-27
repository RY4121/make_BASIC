package newlang4;

import java.lang.reflect.Constructor;
import java.util.EnumSet;
import java.util.Set;

public enum Symbol {
	program(
		EnumSet.of(
			LexicalType.NAME,
			LexicalType.FOR,
			LexicalType.END,
			LexicalType.IF,
			LexicalType.DO
		),
		ProgramNode.class),
	stmt_list(
		EnumSet.of(
			LexicalType.NAME,
			LexicalType.FOR,
			LexicalType.END,
			LexicalType.IF,
			LexicalType.DO
		),
		StatementListNode.class),
	block(
		EnumSet.of(
			LexicalType.IF,
			LexicalType.DO
		),
		BlockNode.class),
	stmt(
		EnumSet.of(
			LexicalType.NAME,
			LexicalType.FOR,
			LexicalType.END
		),
		StatementNode.class),
	expr_list(
		EnumSet.of(
			LexicalType.SUB,
			LexicalType.LP,
			LexicalType.NAME,
			LexicalType.INTVAL,
			LexicalType.DOUBLEVAL,
			LexicalType.LITERAL
		),
		ExprListNode.class),
	if_prefix(
		EnumSet.of(
			LexicalType.NAME
		),
		IfNode.class),
	else_block(
		EnumSet.of(
			LexicalType.ELSE
		),
		null),
	else_if_prefix(
		EnumSet.of(
			LexicalType.NAME
		),
		null),
	subst(
		EnumSet.of(
			LexicalType.NAME
		),
		AssignmentNode.class),
	cond(
		EnumSet.of(
			LexicalType.SUB,
			LexicalType.LP,
			LexicalType.NAME,
			LexicalType.INTVAL,
			LexicalType.DOUBLEVAL,
			LexicalType.LITERAL
		),
		CondNode.class),
	expr(
		EnumSet.of(
			LexicalType.SUB,
			LexicalType.LP,
			LexicalType.NAME,
			LexicalType.INTVAL,
			LexicalType.DOUBLEVAL,
			LexicalType.LITERAL
		),
		ExpressionNode.class),
	var(
		EnumSet.of(
			LexicalType.NAME
		),
		VariableNode.class),
	leftvar(
		EnumSet.of(
			LexicalType.NAME
		),
		VariableNode.class),
	call_func(
		EnumSet.of(
			LexicalType.NAME
		),
		FunctionCallNode.class),
	loop(
		EnumSet.of(
			LexicalType.DO
		),
		LoopNode.class),
	constant(
		EnumSet.of(
			LexicalType.INTVAL,
			LexicalType.DOUBLEVAL,
			LexicalType.LITERAL
		),
		ConstantNode.class),
	;
	Set<LexicalType> first;
	Class<? extends Node> handler;
	Constructor<? extends Node> constructor;

	Symbol(Set<LexicalType> first, Class<? extends Node> handler) {
		this.first = first;
		this.handler = handler;
		try {
			constructor = null;
			if (handler != null) {
				constructor = handler.getConstructor(Environment.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isFirst(LexicalType type) {
//		System.out.println(first);
		return first.contains(type);
	}

	public Node handle(Environment env) throws Exception {
		System.out.println("call Symbol#handle()");
		Node instance = (Node) constructor.newInstance(env);
//		if (instance != null) System.out.println(instance);
		instance.parse();
		return instance;
	}

}
