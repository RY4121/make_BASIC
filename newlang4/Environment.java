package newlang4;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	   LexicalAnalyzer input;
	   Map<String, Variable> var_table;

	    public Environment(LexicalAnalyzer input) {
	    	System.out.println("Environment#Constructor");
	        this.input = input;
	        var_table = new HashMap<String, Variable>();
	    }

	    public LexicalAnalyzer getInput() {
	        return input;
	    }

	    public Variable getVariable(String vname) {
	        Variable v = var_table.get(vname);
	        if (v == null) {
	            v = new Variable(vname);
	            var_table.put(vname, v);
	        }
	        return v;
	    }
}
