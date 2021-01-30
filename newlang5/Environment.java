package newlang5;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	LexicalAnalyzer input;
	Map<String, Function> library; // function
	Map<String, Variable> var_table; // variable

	public Environment(LexicalAnalyzer input) {
		this.input = input;
		library = new HashMap<>();
		library.put("PRINT", new PrintFunction());
		library.put("POW", new Pow());
		library.put("SIN", new Sin());
		var_table = new HashMap<String, Variable>();
	}

	public LexicalAnalyzer getInput() {
		return input;
	}

	public Function getFunction(String fname) {
		return (Function) library.get(fname);
	}

	public Variable getVariable(String vname) {
		Variable v = var_table.get(vname);
		if (v == null) {
			v = new Variable(vname);
			var_table.put(vname, v);
		}
		System.out.println("\tEnv#getVariable()#v.var_name\t" + v.var_name);
		return v;
	}
}
