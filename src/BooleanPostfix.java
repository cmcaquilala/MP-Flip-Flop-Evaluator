
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Stack;

public class BooleanPostfix {
	String postfix = "";
	int value = 0;
	HashMap<String, Integer> vars = new HashMap<String, Integer>();
	
	// Boolean Postfix Evaluator
	// - Takes a postfix expression and a map of variables to solve a boolean function
	//
	// *postfix = postfix expression
	// *vars = variable & value pair
	
	public BooleanPostfix(String postfix, HashMap<String,Integer> vars) {
		this.postfix = postfix;
		this.vars = vars;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public int evaluate() {
		
		StringTokenizer tokenizer = new StringTokenizer(postfix);
		Stack<Integer> values = new Stack<Integer>();
		
		while(tokenizer.hasMoreTokens()) {
			
			String tokenString = tokenizer.nextToken();

			if(isOperator(tokenString)) {
				int a = values.pop();
				int b = values.pop();
				int newValue = operate(a,b,tokenString);
				values.push(newValue);
			} else if(tokenString.equals("\'")) {
				int a = values.pop();
				int newValue = negate(a);
				values.push(newValue);
			} else {
				values.push(getVarValue(tokenString));
			}
		}
		
		this.setValue(values.pop());
			
		return this.getValue();
	}
	
	private boolean isOperator(String x) {
		if(x.equals("+") || x.equals("*") || x.equals("@")) {
			return true;
		}
		return false;
	}
	
	private int getVarValue(String var) {
		if(var.equals("0") || var.equals("1")) {
			return Integer.parseInt(var);
		}
		if(var.charAt(var.length()-1) == '\'') {
			var = var.substring(0, var.length()-1);
			
			if(vars.get(var) == 1) {
				return 0;				
			} else {
				return 1;
			}
		}
		return vars.get(var);
	}
	
	private int operate(int a, int b, String operator) {
		int value = 0;
		if(operator.equals("+")) {
			value = a + b;
			if(value > 1) {
				value = 1;
			}
		} else if (operator.equals("*")) {
			value = a * b;			
		} else if (operator.equals("@")) {
			if(a != b) {
				value = 1;
			}
		}
		return value;
	}
	
	private int negate(int x) {
		if(x == 0) {
			return 1;
		}
		return 0;
	}
}
