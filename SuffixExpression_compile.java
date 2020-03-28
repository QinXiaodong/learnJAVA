import java.util.*;

public class Main {
    public static void main(String[] args) {
        String exp = " 13 + 2 * (9 - 5)";
        SuffixExpression se = compile(exp);
        int result = se.execute();
        System.out.println(exp + " = " + result + " " + (result == 13 + 2 * (9 - 5) ? "✓" : "✗"));
    }

    static SuffixExpression compile(String exp) {
        // TODO:
    	exp = exp.replaceAll("\\s*", "");
    	Map<String,Integer> priority  = new HashMap<>();
    	priority.put("+", 1);
    	priority.put("-", 1);
    	priority.put("*", 2);
    	priority.put("/", 2);
    	priority.put("(", 3);
    	Queue<String> ori = new LinkedList<>();
    	Queue<String> res = new LinkedList<>();
    	Deque<String> stack1 = new LinkedList<>();
    	Deque<String> stack2 = new LinkedList<>();
    	int start = 0, end = 0;
    	for (int i = 0; i < exp.length(); i++) {
    		if (exp.charAt(i) == '+' || 
    				exp.charAt(i) == '-' || 
    				exp.charAt(i) == '*' || 
    				exp.charAt(i) == '/' || 
    				exp.charAt(i) == '(' || 
    				exp.charAt(i) == ')') {
    			if(start != i) {
    				ori.offer(exp.substring(start, i));
        			start = i+1;
    			} else {
    				start = i+1;
    			}
    			ori.offer(exp.substring(i,i+1));
    		}
    	}
    	String temp;
    	while ((temp = ori.poll()) != null) {
    		if (temp.equals("+") || temp.equals("-") || temp.equals("*") || temp.equals("/") || temp.equals("(") || temp.equals(")")) {
    			if (!temp.equals(")")) {
    				while(!stack1.isEmpty() && !stack1.peek().equals("(") && priority.get(temp) <= priority.get(stack1.peek())) {
    					res.offer(stack1.pop());
    				}
    				stack1.push(temp);
    			} else {
    				while(!stack1.isEmpty() && !stack1.peek().equals("(")) {
    					res.offer(stack1.pop());
    				}
    				stack1.pop();
    			}
    		} else {
    			res.offer(temp);
    		}
    	}
    	while (!stack1.isEmpty()) {
    		res.offer(stack1.pop());
    	}
//    	System.out.println(res.toString());
        return new SuffixExpression(res);
    }
}

class SuffixExpression {
	private Queue<String> exp;
    public SuffixExpression(Queue<String> exp) {
    	this.exp = exp;
    }
	int execute() {
        // TODO:
		Deque<Integer> stack = new LinkedList<>();
		while (!exp.isEmpty()) {
			String temp = exp.poll();
			if (temp.equals("+") || 
					temp.equals("-") || 
					temp.equals("*") || 
					temp.equals("/")) {
				int var2 = stack.pop();
				int var1 = stack.pop();
				switch (temp) {
				case "+":
					stack.push(var1 + var2);
					break;
				case "-":
					stack.push(var1 - var2);
					break;
				case "*":
					stack.push(var1 * var2);
					break;
				case "/":
					stack.push(var1 / var2);
					break;
				default:
					break;
				}
			} else {
				stack.push(Integer.valueOf(temp));
			}
		}
        return stack.pop();
    }
}
