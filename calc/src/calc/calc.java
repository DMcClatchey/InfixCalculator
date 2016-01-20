//David McClatchey CSc3410

/*Program purpose: The purpose of the program is to take an infix 
mathematical expression from the user, convert that expression 
to a postfix expression, and then calculate the result using the 
postfix expression. There is a main method, an infix-to-postfix 
method, and a postfix-calculation method. The user can enter 
spaces, digits, the letter 'x', *, +, /, -, %, '(', and ')'. There 
can be as many spaces between characters as the user desires. 
However, if a character follows another character that it is not 
permitted to follow, even with spaces in between, such as "(*" or 
"(   *", then the method for converting to postfix returns 
"Error." If the user enters 'x' in the infix expression, then the 
postfix calculation method prompts the user to enter the number 
value of 'x' when it is time to calculate and shows the new 
postfix string with 'x' replaced by the value. At the end of the 
program, it displays the final result of the original expression.

Solution and algorithms: My solution includes a main method, an 
infix-to-postfix method, and a postfix-calculation method. The 
infix-to-postfix method contains a for loop algorithm that deals
with each operand and operator, rearranging them using a stack
to get a postfix expression. This algorithm also checks for
any character sequences that are not valid by checking the
character that comes before the current character to make sure
that sequence of characters is permissible. The postfix-
calculation method does not need to check for user entry errors
as they have all been found in the infix-to-postfix method. So,
once a postfix expression reaches the postfix-calculation 
method, it will always be a valid expression to calculate. In
the postfix-calculation method, another for loop algorithm is 
used to cycle through the expression and convert string numbers
to int numbers that get stacked onto an Integer stack until an
operator is found, at which point the top two Integers are
popped from the stack and evaluated with the given operator. The
result from this is then pushed back onto the stack. Once the
for loop is through in the postfix-calculation method, only the 
result remains on the stack, and this result gets popped and 
returned.

Data structures used: Stack for operators in infix-to-postfix
method and postfix calculation method, ArrayList and array in 
infix-to-postfix method that holds operands and operators in 
postfix order, and at the end of the method gets converted to 
a string and returned.

How to use the program, expected input/output: From a user 
perspective, the program is simple. You input a mathematical
infix expression, and the output gives you a postfix
expression and the result. If your input contains 'x', then you
must input the value of 'x' before a calculation can be done.
If any errors are encountered along the way, the program output
is "Error".

Method pre-conditions and post-conditions:

	infixtopostfix preconditions: This method takes a string
	parameter that is the infix input from the user. This
	method takes any string even if it is not a valid infix
	expression. Invalid inputs are determined in the method.
	
	infixtopostfix postconditions: The method takes the string
	parameter and rearranges it to a postfix expression, 
	returning a string of the postfix expression. If the string
	parameter is not a valid infix expression, then the method 
	returns the string "Error". The returned postfix expression 
	has has spaces after every number to show when a number,
	important in the case where there are two numbers in a row.
	
	postfixcalculation preconditions: This method takes a string
	representing a postfix expression. Any invalid postfix
	expressions have been found before reaching this method, so
	the postfix string parameter is always a valid postfix 
	expression.
	
	postfixcalculation postconditions: This method takes the
	postfix string parameter and stacks any numbers as Integers
	on an Integer stack, popping the top two Integers to use in 
	a calculation whenever an operator is found in the 
	expression. The method cycles through each character in the
	postfix expression, ignoring spaces, until all calculations
	are complete and only the result remains on the stack. This
	result then gets popped from the stack and returned.
*/

package calc;
import java.util.*;

public class calc {

	public static void main(String[] args) {
		String infixinput = new String();
		Scanner s = new Scanner(System.in);
		System.out.println("Enter infix expression");
		infixinput= s.nextLine();
		
		String thepostfix = new String();
		thepostfix = infixtopostfix(infixinput);
		
		System.out.println("Here is your postfix expression: " + thepostfix);
		
		if(thepostfix!="Error"){
			System.out.println("Here is the result: " + postfixcalc(thepostfix));
		}
	}
	
	public static String infixtopostfix(String infix){
		Stack<String> operatorstack = new Stack<String>();
		ArrayList<String> pfix = new ArrayList();//postfix list to be converted to one String and returned
		
		
		//delete spaces at beginning of infix string
		while(infix.charAt(0) == ' '){
			infix = infix.substring(1,infix.length());
		}
		
		
		//deletes spaces at end of infix string
		while(infix.charAt(infix.length()-1) == ' '){
			infix = infix.substring(0,infix.length()-1);
		}
		
		
		//if 0th char isn't digit, isn't left parenthesis, and isn't 'x', return error
		if(!Character.isDigit(infix.charAt(0)) && infix.charAt(0)!='(' && infix.charAt(0)!='x' ){
			return "Error";
		}
		
		
		//if last char isn't digit, isn't right parenthesis, and isn't 'x', return error
		if(!Character.isDigit(infix.charAt(infix.length()-1)) && infix.charAt(infix.length()-1)!=')' && infix.charAt(infix.length()-1)!='x' ){
			return "Error";
		}
		
		
		//loop through string, starts with digit, 'x', or left parenthesis
		for(int i=0; i<infix.length(); i++){
			
			
			//beyond i=0, make sure character is 'x', digit, operator, space, or parenthesis, else return error
			if(     !Character.isDigit(infix.charAt(i)) 
					&& infix.charAt(i) != '+' 
					&& infix.charAt(i) != '-' 
					&& infix.charAt(i) != '*' 
					&& infix.charAt(i) != '/' 
					&& infix.charAt(i) != '%' 
					&& infix.charAt(i) != ' '
					&& infix.charAt(i) != '('
					&& infix.charAt(i) != ')'
					&& infix.charAt(i) != 'x'
					){
				return "Error";
			}
			
			
			//if operand, creates string of digit(s) that will be a postfix operand
			if(i!=0 && Character.isDigit(infix.charAt(i)) 
					&& ((infix.charAt(i-1)=='x') 
						|| (infix.charAt(i-1)==')'))){
				return "Error";
			}
			else if((i==0 && Character.isDigit(infix.charAt(i))) 
							  ||(i!=0 
								&& Character.isDigit(infix.charAt(i)) 
								&& (infix.charAt(i-1)!='x')
								&& (infix.charAt(i-1)!=')'))){
				String operand = new String();
				while(Character.isDigit(infix.charAt(i))){
					operand = operand + infix.charAt(i);
					i++;
					if(i==infix.length()){
						break;
					}
				}
				if (operand.length()>0){
					pfix.add(operand);//if there's an operand, adds it to pfix arraylist
					pfix.add(" ");//creates a space entry after an operand entry
				}
			}
			
			
			//dealing with a space character
			if(i<infix.length() && infix.charAt(i)==' '){
				
				int j = i;
				while(infix.charAt(i+1)==' '){
					i++;//i becomes position of last space character, j holds position of first space character
				}
				
				//case where digit followed by digit, 'x', or open parenthesis with space(s) in between, error
				if(Character.isDigit(infix.charAt(j-1)) && 
						(Character.isDigit(infix.charAt(i+1))
						|| infix.charAt(i+1)=='x'
						|| infix.charAt(i+1)=='(')){
					return "Error";
				}
				
				//case where 'x' followed by digit, 'x', or open parenthesis with space(s) in between, error
				if(infix.charAt(j-1)=='x' && 
						(Character.isDigit(infix.charAt(i+1))
						|| infix.charAt(i+1)=='x'
						|| infix.charAt(i+1)=='(')){
					return "Error";
				}
				
				//case where operator or open parenthesis follows another operator or close parenthesis, with space(s) in between, error
				if(	     (infix.charAt(j-1)=='+'
					   || infix.charAt(j-1)=='-'
					   || infix.charAt(j-1)=='*'
					   || infix.charAt(j-1)=='/'
					   || infix.charAt(j-1)=='%'
					   || infix.charAt(j-1)=='(')
					   &&
					     (infix.charAt(i+1)=='+'
					   || infix.charAt(i+1)=='-'
					   || infix.charAt(i+1)=='*'
					   || infix.charAt(i+1)=='/'
					   || infix.charAt(i+1)=='%'
					   || infix.charAt(i+1)==')')	  
				   ){
				  return "Error";	
				}
				
				
				//case where closed parenthesis precedes open parenthesis, digit, or 'x' with space(s) between, error
				if(infix.charAt(j-1)==')' &&
						(Character.isDigit(infix.charAt(i+1))
						||infix.charAt(i+1)=='('
						||infix.charAt(i+1)=='x')){
					return "Error";
				}			
			}
			
			
			//if 'x', add "x" to pfix arraylist, 'x' can be preceded in infix by anything but 'x', digit, or close parenthesis
			if(i==0 && infix.charAt(i)=='x'){
				pfix.add("x");
				pfix.add(" ");
			}
			else if(i!=0 && i<infix.length() && infix.charAt(i)=='x' &&
						(infix.charAt(i-1)=='x' 
						|| infix.charAt(i-1)==')' 
						|| Character.isDigit(infix.charAt(i-1)))){
						return "Error";
			}
			else if(i!=0 
					&& i<infix.length()
					&& infix.charAt(i)=='x' 
					&& infix.charAt(i-1)!='x'						
					&& (infix.charAt(i-1)!=')')){
				pfix.add("x");
				pfix.add(" ");
			}
			
			
			//open parenthesis highest priority, goes onto stack, must be preceded by operator or space unless at position 0
			if(i<infix.length() && infix.charAt(i)=='('){
				if(i!=0 && (Character.isDigit(infix.charAt(i-1)) || infix.charAt(i-1)==')')){
					return "Error";
				}
				else
					operatorstack.push("(");
			}
		
			
			//closed parenthesis, must be preceded by 'x', digit or space, pop everything on operator stack down to open parenthesis, check if open parenthesis is found in stack
			if(i<infix.length() && infix.charAt(i)==')'){
				if(!Character.isDigit(infix.charAt(i-1)) && infix.charAt(i-1)!=' ' && infix.charAt(i-1)!='x'){
					return "Error";
				}
				else if(operatorstack.isEmpty() || operatorstack.search("(") == -1){
					return "Error";
				}
				else {
					//pops all operator strings to pfix array while they aren't open parenthesis
					while(operatorstack.peek()!="("){
						pfix.add(operatorstack.pop());
					}
					//pops open parenthesis once all operator strings have been popped to pfix array
					if(operatorstack.peek()=="("){
						operatorstack.pop();
					}
				}	
			}
			
			
			//Case for multiplication symbol, must be preceded by 'x', digit, space, or closed parenthesis
			if(i<infix.length() && infix.charAt(i)=='*'){
				if(operatorstack.isEmpty()){
					operatorstack.push("*");
				}
				else if(!Character.isDigit(infix.charAt(i-1)) && infix.charAt(i-1)!=')' && infix.charAt(i-1)!=' ' && infix.charAt(i-1)!='x'){
					return "Error";
				}
				else if (operatorstack.peek()=="/" 
						|| operatorstack.peek()=="*"
						|| operatorstack.peek()=="%"){
					while(operatorstack.peek()=="/" 
							|| operatorstack.peek()=="*"
							|| operatorstack.peek()=="%"){		
						pfix.add(operatorstack.pop());
						if(operatorstack.isEmpty()){
							break;
						}
					}
					operatorstack.push("*");
				}
				else{
					operatorstack.push("*");
				}
			}
			
			
			//Case for division symbol, must be preceded by 'x', digit, space, or closed parenthesis
			if(i<infix.length() && infix.charAt(i)=='/'){
				if(operatorstack.isEmpty()){
					operatorstack.push("/");
				}
				else if(!Character.isDigit(infix.charAt(i-1)) && infix.charAt(i-1)!=')' && infix.charAt(i-1)!=' ' && infix.charAt(i-1)!='x'){
					return "Error";
				}
				else if (operatorstack.peek()=="/" 
						|| operatorstack.peek()=="*"
						|| operatorstack.peek()=="%"){
					while(operatorstack.peek()=="/" 
							|| operatorstack.peek()=="*"
							|| operatorstack.peek()=="%"){		
						pfix.add(operatorstack.pop());
						if(operatorstack.isEmpty()){
							break;
						}
					}
					operatorstack.push("/");
				}
				else{
					operatorstack.push("/");
				}
			}
			
			
			//Case for modulus symbol, must be preceded by 'x' digit, space, or closed parenthesis
			if(i<infix.length() && infix.charAt(i)=='%'){
				if(operatorstack.isEmpty()){
					operatorstack.push("%");
				}
				else if(!Character.isDigit(infix.charAt(i-1)) && infix.charAt(i-1)!=')' && infix.charAt(i-1)!=' ' && infix.charAt(i-1)!='x'){
					return "Error";
				}
				else if (operatorstack.peek()=="/" 
						|| operatorstack.peek()=="*"
						|| operatorstack.peek()=="%"){
					while(operatorstack.peek()=="/" 
							|| operatorstack.peek()=="*"
							|| operatorstack.peek()=="%"){		
						pfix.add(operatorstack.pop());
						if(operatorstack.isEmpty()){
							break;
						}
					}
					operatorstack.push("%");
				}
				else{
					operatorstack.push("%");
				}
			}
			
			
			//Case for addition symbol, must be preceded by 'x' digit, space or closed parenthesis
			if(i<infix.length() && infix.charAt(i)=='+'){
				if(operatorstack.isEmpty()){
					operatorstack.push("+");
				}
				else if(!Character.isDigit(infix.charAt(i-1)) && infix.charAt(i-1)!=')' && infix.charAt(i-1)!=' ' && infix.charAt(i-1)!='x'){
					return "Error";
				}
				else if (operatorstack.peek()=="/" 
						|| operatorstack.peek()=="*"
						|| operatorstack.peek()=="%"
						|| operatorstack.peek()=="+"
						|| operatorstack.peek()=="-"
						|| operatorstack.peek()=="("){
					while(operatorstack.peek()=="/" 
							|| operatorstack.peek()=="*"
							|| operatorstack.peek()=="%"
							|| operatorstack.peek()=="+"
							|| operatorstack.peek()=="-"){		
						pfix.add(operatorstack.pop());
						if(operatorstack.isEmpty()){
							break;
						}
					}
					operatorstack.push("+");
				}
			}
		
			
			//Case for subtraction symbol, must be preceded by 'x', digit, or closed parenthesis
			if(i<infix.length() && infix.charAt(i)=='-'){
				if(operatorstack.isEmpty()){
					operatorstack.push("-");
				}
				else if(!Character.isDigit(infix.charAt(i-1)) && infix.charAt(i-1)!=')' && infix.charAt(i-1)!=' ' && infix.charAt(i-1)!='x'){
					return "Error";
				}
				else if (operatorstack.peek()=="/" 
						|| operatorstack.peek()=="*"
						|| operatorstack.peek()=="%"
						|| operatorstack.peek()=="+"
						|| operatorstack.peek()=="-"
						|| operatorstack.peek()=="("){
					while(operatorstack.peek()=="/" 
							|| operatorstack.peek()=="*"
							|| operatorstack.peek()=="%"
							|| operatorstack.peek()=="+"
							|| operatorstack.peek()=="-"){		
						pfix.add(operatorstack.pop());
						if(operatorstack.isEmpty()){
							break;
						}
					}
					operatorstack.push("-");
				}
			}
			
		}
		
		while(!operatorstack.isEmpty()){
			if(operatorstack.peek() == "("){
				return "Error";
			}
			else{	
				pfix.add(operatorstack.pop());
			}
		}
		
		
		//converts pfix ArrayList to array, uses string builder to create a single string from the array, then converts stringbuilder to string and returns
		String[] thepfixarray = pfix.toArray(new String[pfix.size()]);
		StringBuilder stringtoreturn = new StringBuilder();
		for (int m = 0; m < thepfixarray.length; m++) {
		   stringtoreturn.append( thepfixarray[m] );
		}
		
		return stringtoreturn.toString();
	}
	
	//calculate postfix method
	public static int postfixcalc(String postfix){
		Stack<Integer> operatorstack = new Stack<Integer>();
		
		//if 'x' exists in postfix, user inputs number value to replace
		if(postfix.contains("x")){
			Scanner s = new Scanner(System.in);
			String xvalue = new String();
			System.out.println("Enter value of 'x'");
			xvalue = s.next();
			postfix = postfix.replaceAll("x", xvalue);
			System.out.println("Your new postfix with 'x' replaced is:"+ postfix);
		}
		
		
		//loop through postfix doing calculations along the way, spaces ignored
		for(int i=0; i<postfix.length(); i++){
			
			
			//case where a number is found, create operand int and push to stack
			String operand = new String();
			while(Character.isDigit(postfix.charAt(i))){
				operand = operand + postfix.charAt(i);
				i++;
				if(i==postfix.length()){
					break;
				}
			}
			if (operand.length()>0){
				int operandinteger = Integer.parseInt(operand);//change operand to int
				operatorstack.push(operandinteger);//if there's an operand, push to stack
			}
			
			
			//addition case, calculates and pushes result to stack
			if(postfix.charAt(i)=='+'){
				
				int secondoperand = operatorstack.pop();
				int firstoperand = operatorstack.pop();
				int result = firstoperand+secondoperand;
				operatorstack.push(result);
			}
			
			
			//subtraction case, calculates and pushes result to stack
			if(postfix.charAt(i)=='-'){
				
				int secondoperand = operatorstack.pop();
				int firstoperand = operatorstack.pop();
				int result = firstoperand-secondoperand;
				operatorstack.push(result);
			}
				
			
			//multiplication case, calculates and pushes result to stack
			if(postfix.charAt(i)=='*'){
				
				int secondoperand = operatorstack.pop();
				int firstoperand = operatorstack.pop();
				int result = firstoperand*secondoperand;
				operatorstack.push(result);
			}
			
			
			//division case, calculates and pushes back to stack
			if(postfix.charAt(i)=='/'){
				
				int secondoperand = operatorstack.pop();
				int firstoperand = operatorstack.pop();
				int result = firstoperand/secondoperand;
				operatorstack.push(result);
			}
			
			
			//modulus case, calculates and pushes back to stack
			if(postfix.charAt(i)=='%'){
				
				int secondoperand = operatorstack.pop();
				int firstoperand = operatorstack.pop();
				int result = firstoperand%secondoperand;
				operatorstack.push(result);
			}
	   	
		}
		//pops final result after loop through all postfix positions
		return operatorstack.pop();
	}

}
