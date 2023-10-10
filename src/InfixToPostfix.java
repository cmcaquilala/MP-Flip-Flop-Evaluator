
import java.util.StringTokenizer;
import java.util.Stack;

public class InfixToPostfix {
	private static String[] operators = {"+","*",")","(","@"};
	private String infix;

	public InfixToPostfix(String infix){
		this.infix = infix;
	}
	
	public String convertToPostFix(){
		StringBuilder converted = new StringBuilder();
		Stack<String> operatorStack = new Stack<String>();
		
		// temporarily removes spaces to check for variables next to each other
		this.infix = this.infix.replaceAll(" ","");
		
		// adds the multiplication sign between two variables or a parenthesis and a variable
		for(int i = 0; i < this.infix.length()-1; i++) {
			String char1 = this.infix.charAt(i) + "";
			String char2 = this.infix.charAt(i+1) + "";
			
			if(isValidMultiply(char1, char2)) {
				this.infix = this.infix.replace(char1+char2, char1+"*"+char2);
			}
		}
		
		// adds spaces
		for(int i = 0; i < operators.length; i++) {
			this.infix = this.infix.replace(operators[i], " " + operators[i] + " ");
		}
		
		StringTokenizer tokenizer = new StringTokenizer(this.infix);
		
		while(tokenizer.hasMoreTokens()) {
			String x = tokenizer.nextToken();
			
			if (isOperator(x) && !x.equals(")") && !x.equals("(")) {
				
				int precx = GetPrecedence(x.charAt(0));
				
				while(operatorStack.isEmpty() == false &&
						GetPrecedence(operatorStack.peek().toString().charAt(0)) > precx) {
					converted.append(operatorStack.pop() + " ");
				}
				operatorStack.push(x);
				
			} else if (x.equals("(")) {
				operatorStack.push(x);
			} else if (x.equals(")")) {
				while(operatorStack.peek().equals("(") == false) {
					converted.append(operatorStack.pop() + " ");
				}
				operatorStack.pop();
			}else {
				converted.append(x + " ");
			}
		}
		
		while(operatorStack.isEmpty() == false) {
			converted.append(operatorStack.pop() + " ");
		}
		
		this.infix = converted.toString();
		return this.infix;
	}
	
	private static boolean isOperator(String x) {
		for(int i = 0; i < operators.length; i++) {
			if(x.equals(operators[i])) {
				return true;
			}
		}
		return false;
	}
	
	private static int GetPrecedence(char x) {
		if(x == '+') {
			return 1;
		} else if (x == '@') {
			return 2;
		} else if (x == '*')
			return 3;
		return 0;
	}
	
	private static boolean isValidMultiply(String ch1, String ch2) {
		if(!isOperator(ch1) && !isOperator(ch2) && !ch2.equals("\'")) {
			// xy
			return true;
		} if(!isOperator(ch1) && ch2.equals("(")) {
			// x(
			return true;
		} if(ch1.equals(")") && (ch2.equals("(") || !isOperator(ch2) )) {
			// )( or )x
			return true;
		}
		
		return false;
	}
	
}
