

import java.util.*;
import java.io.*;
import java.lang.Object;

class UnderflowException extends Exception {
	public UnderflowException() {
	}

	public UnderflowException(String msg) {
		super(msg);
	}
}

class ArrayStack {
	public String[] theArray;
	public int topOfStack;
	public static final int Default_Capacity = 10;

	public ArrayStack() {
		theArray = new String[Default_Capacity];
		topOfStack = -1;
	}

	public void push(String x) {
		if (topOfStack + 1 == theArray.length)
			theArray = Arrays.copyOf(theArray, (theArray.length * 2));
		theArray[++topOfStack] = x;
	}

	public void pop() throws UnderflowException {
		try {
			if (topOfStack == -1)
				throw new UnderflowException("Empty Stack");
			topOfStack--;
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
	}

	public String top() throws UnderflowException {
		try {
			if (topOfStack == -1)
				throw new UnderflowException("Empty Stack");
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
		return theArray[topOfStack];
	}

	public int precedence(String op) {
		int prec = 0;
		if (op.equals("!"))
			prec = 0;
		if (op.equals("("))
			prec = 1;
		if (op.equals("+") || op.equals("-"))
			prec = 2;
		else if (op.equals("*") || op.equals("/") || op.equals("%"))
			prec = 3;
		else if (op.equals("^"))
			prec = 4;
		return prec;
	}

	public boolean isOperator(String opr) {
		return (opr.equals("+") || opr.equals("-") || opr.equals("*") || opr.equals("/") || opr.equals("^")
				|| opr.equals("%"));
	}

	public boolean isDigits(String string) {
		Character d;
		for (char ch : string.toCharArray()) {
			d = new Character(ch);
			if ((!d.equals('.')) && (!Character.isDigit(ch)))
				return false;
		}
		return true;
	}

	public String infixToPostfix(String expression) throws UnderflowException {
		int delim = 0;
		String outputArray = new String();
		String temp = null;
		String[] out = expression.split("(?<=[-+*/\\(\\)\\s])|(?=[-+*/\\(\\)])");
		try {
			if (isOperator(out[0])) {
				System.out.println("Invalid Expression");
				System.out.println("Expression cannot start with an operator");
				return "Can't convert";
			} else {
				for (String token : out) {
					token = token.trim();
					if (token.isEmpty())
						continue;
					else if (isDigits(token)) {
						outputArray += " ";
						outputArray += token;
					} else if (token.equals("(")) {
						delim += 1;
						push(token);
					} else if (token.equals(")")) {
						delim -= 1;
						temp = top();
						while (!temp.equals("(")) {
							pop();
							outputArray += " ";
							outputArray += temp;
							temp = top();
						}
						pop();
					} else if (isOperator(token)) {
						while (precedence(token) <= precedence(top()) && (!(top() == "("))) {
							temp = top();
							pop();
							outputArray += " ";
							outputArray += temp;
						}
						push(token);
					} else if (token.matches("(.*)[a-z|A-Z|#](.*)")) {
						System.out.println("Invalid character: " + token);
						System.out.println("Expression is invalid");
						topOfStack = -1;
						return "Can't convert";
					}
				}
				if (delim != 0) {
					System.out.println("Invalid Expression");
					System.out.println("Delimiter Mismatch");
					return "Can't convert";
				}
				while (topOfStack != 0) {
					temp = top();
					pop();
					outputArray += " ";
					outputArray += temp;
				}
				topOfStack = -1;
			}
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
		return outputArray;
	}

	public float postfixEval(String pArray) throws UnderflowException {
		float result = 0;
		float x, y;
		String t, s = null;
		try {
			if (pArray.equals("Can't convert")) {
				return (0);
			} else {
				String[] res = pArray.split("\\s");
				char ch;
				for (String r : res) {
					r = r.trim();
					if (r.isEmpty())
						continue;
					else if (isDigits(r))
						push(r);
					else if (isOperator(r)) {
						t = top();
						x = Float.parseFloat(t);
						pop();
						t = top();
						y = Float.parseFloat(t);
						pop();
						if (r.equals("+"))
							result = (float) y + x;
						else if (r.equals("-"))
							result = (float) y - x;
						else if (r.equals("*"))
							result = (float) y * x;
						else if (r.equals("/"))
							result = (float) (y / x);
						else if (r.equals("%"))
							result = (float) (y % x);
						push(Float.toString(result));
					}
				}
			}
			topOfStack = -1;
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
		return result;
	}
}

class ListStack {
	public ListNode topOfStack = null;

	public void push(String string) {
		topOfStack = new ListNode(string, topOfStack);
	}

	public void pop() throws UnderflowException {
		try {
			if (topOfStack == null)
				throw new UnderflowException("ListStack pop");
			topOfStack = topOfStack.next;
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
	}

	public String top() throws UnderflowException {
		try {
			if (topOfStack == null)
				throw new UnderflowException("Empty list stack");
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
		return topOfStack.element;
	}

	public boolean isDigits(String string) {
		Character d;
		for (char ch : string.toCharArray()) {
			d = new Character(ch);
			if ((!d.equals('.')) && (!Character.isDigit(ch)))
				return false;
		}
		return true;
	}

	public int precedence(String op) {
		int prec = 0;
		if (op.equals("!"))
			prec = 0;
		if (op.equals("("))
			prec = 1;
		if (op.equals("+") || op.equals("-"))
			prec = 2;
		else if (op.equals("*") || op.equals("/") || op.equals("%"))
			prec = 3;
		else if (op.equals("^"))
			prec = 4;
		return prec;
	}

	public boolean isOperator(String opr) {
		return (opr.equals("+") || opr.equals("-") || opr.equals("*") || opr.equals("/") || opr.equals("^")
				|| opr.equals("%"));
	}

	public String infixToPostfix(String expression) throws UnderflowException {
		int delim = 0;
		String outputArray = new String();
		String temp = null;
		String[] out = expression.split("(?<=[-+*/\\(\\)\\s])|(?=[-+*/\\(\\)])");
		try {
			if (isOperator(out[0])) {
				System.out.println("Invalid Expression");
				System.out.println("Expression cannot start with an operator");
				return "Can't convert";
			} else {
				for (String token : out) {
					token = token.trim();
					if (token.isEmpty())
						continue;
					else if (isDigits(token)) {
						outputArray += " ";
						outputArray += token;
					} else if (token.equals("(")) {
						delim += 1;
						push(token);
					} else if (token.equals(")")) {
						delim -= 1;
						temp = top();
						while (!temp.equals("(")) {
							pop();
							outputArray += " ";
							outputArray += temp;
							temp = top();
						}
						pop();
					} else if (isOperator(token)) {
						while (precedence(token) <= precedence(top()) && (!(top() == "("))) {
							temp = top();
							pop();
							outputArray += " ";
							outputArray += temp;
						}
						push(token);
					} else if (token.matches("(.*)[a-z|A-Z|#](.*)")) {
						System.out.println("Invalid character: " + token);
						System.out.println("Expression is invalid");
						topOfStack = null;
						return "Can't convert";
					}
				}
				if (delim != 0) {
					System.out.println("Invalid Expression");
					System.out.println("Delimiter Mismatch");
					return "Can't convert";
				}
				while (topOfStack != null) {
					temp = top();
					pop();
					outputArray += " ";
					outputArray += temp;
				}
				topOfStack = null;
			}
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
		return outputArray;
	}

	public float postfixEval(String pArray) throws UnderflowException {
		float result = 0;
		float x, y;
		String t, s = null;
		try {
			if (pArray.equals("Can't convert")) {
				return (0);
			} else {
				String[] res = pArray.split("\\s");
				char ch;
				for (String r : res) {
					r = r.trim();
					if (r.isEmpty())
						continue;
					else if (isDigits(r))
						push(r);
					else if (isOperator(r)) {
						t = top();
						x = Float.parseFloat(t);
						pop();
						t = top();
						y = Float.parseFloat(t);
						pop();
						if (r.equals("+"))
							result = (float) y + x;
						else if (r.equals("-"))
							result = (float) y - x;
						else if (r.equals("*"))
							result = (float) y * x;
						else if (r.equals("/"))
							result = (float) (y / x);
						else if (r.equals("%"))
							result = (float) (y % x);
						push(Float.toString(result));
					}
				}
			}
			topOfStack = null;
		} catch (UnderflowException e) {
			e.printStackTrace();
		}
		return result;
	}
} // liststack close

class ListNode {
	public String element;
	public ListNode next;

	public ListNode(String theElement) {
		this(theElement, null);
	}

	public ListNode(String theElement, ListNode n) {
		element = theElement;
		next = n;
	}
}

class InfixToPostFix {
	public static void main(String args[]) throws UnderflowException {
		int count = 0;
		String postfixExpr = null;
		String sArray[] = new String[100];
		String token = null;
		String expr = null;
		float fresult;
		try {
			Scanner sc = new Scanner(new FileReader(args[0]));
			for (int i = 0; sc.hasNextLine(); i++) {
				sArray[i] = sc.nextLine();
				count++;
			}
			ArrayStack obj = new ArrayStack();
			System.out.println("\nStart of expression evaluations using Dynamic Array implementation");
			for (int j = 0; j < count; j++) {
				System.out.println("\nInfix Expression read from input\n" + sArray[j]);
				obj.push("!");
				postfixExpr = obj.infixToPostfix(sArray[j]);
				if (postfixExpr.equals("Can't convert"))
					continue;
				System.out.println("The expression in Postfix notation\n" + postfixExpr);
				fresult = obj.postfixEval(postfixExpr);
				System.out.println("The Arithmetic result = " + fresult);
			}
			ListStack obj1 = new ListStack();
			System.out.println("\n\nStart of expression evaluations using Linked List implementation");
			for (int k = 0; k < count; k++) {
				System.out.println("\nInfix Expression read from input\n" + sArray[k]);
				obj1.push("!");
				postfixExpr = obj1.infixToPostfix(sArray[k]);
				if (postfixExpr.equals("Can't convert"))
					continue;
				System.out.println("The expression in Postfix notation \n" + postfixExpr);
				fresult = obj1.postfixEval(postfixExpr);
				System.out.println("The Arithmetic result = " + fresult);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
