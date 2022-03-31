package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Razred koji provjerava funkcionalnost razreda ObjectStack. 
 * @author teakr
 *
 */
public class StackDemo {

	public static void main(String[] args) {
		
		ObjectStack stack = new ObjectStack();
		String[] elements = args[0].split(" ");
			
		int a;
		int b;
		int result;
		
		for(String element : elements) {
			try {
				int helper = Integer.parseInt(element);
				stack.push(helper);
			}catch(NumberFormatException e) {
				System.out.println();
				b = (int) stack.pop();
				a = (int) stack.pop();
				switch(element) {
					case "+":
						result = a + b;
						stack.push(result);
						break;
					case "-":
						result = a - b;
						stack.push(result);
						break;
					case "*":
						result = a * b;
						stack.push(result);
						break;
					case "/":
						if(b == 0)
							System.out.println("Division with 0!");
						result = a / b;
						stack.push(result);
						break;
					default:
						throw new IllegalArgumentException("Wrong argument:" + element);
				}
				
			}catch(Exception e) {
				System.out.println("Error, something went wrong!");
				System.exit(-1);
			}
		}
		
		if(stack.size() != 1) {
			System.out.println("Error, something went wrong!");
			System.exit(-1);
		}
		
		System.out.println(args[0] + "Expression evaluates to " + stack.pop() + ".");
	}

}
