import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.io.FileNotFoundException;
import java.io.File;

/** Class to store a node of expression tree
    For each internal node, element contains a binary operator
    List of operators: +|*|-|/|%|^
    Other tokens: (|)
    Each leaf node contains an operand (long integer)
*/

public class Expression {
    public enum TokenType {  // NIL is a special token that can be used to mark bottom of stack
	PLUS, TIMES, MINUS, DIV, MOD, POWER, OPEN, CLOSE, NIL, NUMBER
    }
    
    public static class Token {
	TokenType token;
	int priority; // for precedence of operator
	Long number;  // used to store number of token = NUMBER
	String string;

	Token(TokenType op, int pri, String tok) {
	    token = op;
	    priority = pri;
	    number = null;
	    string = tok;
	}

	// Constructor for number.  To be called when other options have been exhausted.
	Token(String tok) {
	    token = TokenType.NUMBER;
	    number = Long.parseLong(tok);
	    string = tok;
	}
	
	boolean isOperand() { return token == TokenType.NUMBER; }

	public long getValue() {
	    return isOperand() ? number : 0;
	}

	public String toString() { return string; }
    }

    Token element;
    Expression left, right;

    // Create token corresponding to a string
    // tok is "+" | "*" | "-" | "/" | "%" | "^" | "(" | ")"| NUMBER
    // NUMBER is either "0" or "[-]?[1-9][0-9]*
    static Token getToken(String tok) {  // To do
	Token result;
	switch(tok) {
	case "+":
	    result = new Token(TokenType.PLUS, 1, tok);  // modify if priority of "+" is not 1
	    break;
	case "-":
		result = new Token(TokenType.MINUS,1,tok);
		break;
	case "/":
		result = new Token(TokenType.DIV,2,tok);
		break;
	case "*":
		result = new Token(TokenType.TIMES,2,tok);
		break;
	case "%":
		result = new Token(TokenType.MOD,2,tok);
		break;
	case "^":
		result = new Token(TokenType.POWER,3,tok);
		break;
	case "(":
		result = new Token(TokenType.OPEN,4,tok);
		break;
	case ")":
		result = new Token(TokenType.CLOSE,4,tok);
		break;
	default:
	    result = new Token(tok);
	    break;
	}
	return result;
    }
    
    private Expression() {
	element = null;
    }
    
    private Expression(Token oper, Expression left, Expression right) {
	this.element = oper;
	this.left = left;
	this.right = right;
    }

    private Expression(Token num) {
	this.element = num;
	this.left = null;
	this.right = null;
    }

    // Given a list of tokens corresponding to an infix expression,
    // return the expression tree corresponding to it.
    public static Expression infixToExpression(List<Token> exp) 
    {  
    	//initializing expression and operator stacks, and using iterator for list parameter.
    	Iterator <Token> i = exp.iterator(); 
    	ArrayDeque <Token> op = new ArrayDeque<>();
    	ArrayDeque <Expression> tree = new ArrayDeque<>();
    	Token nil = new Token(TokenType.NIL,0,"");
    	op.push(nil);
    	//initializing tree expressions, tok and root token, and precedence is used for parentheses.
    	Expression right,left;
    	Token tok, root;
    	int precedence = 1;
    	//loop through each element in exp
    	while(i.hasNext()) 
    	{
    		//tok = current element in exp
    		tok = i.next();
    		//push tok to exp stack (tree)
    		if(tok.isOperand()) 
    		{
    			Expression temp = new Expression(tok);
    			tree.push(temp);
    		}
    		else
    		{
    			//push an open parentheses to op stack and set precedence to 5 (int > 4)
    			if(tok.token == TokenType.OPEN)
    			{
    				op.push(tok);
    				precedence *= 5;
    			}
    			
    			//if it's a close parentheses, then continue creating subtrees until open parentheses, then reset precedence.
    			else if(tok.token == TokenType.CLOSE)
    			{
    				while(op.peek().token != TokenType.OPEN)
    				{
    					right = tree.pop();
    					left = tree.pop();
    					root = op.pop();
    					Expression subtree = new Expression(root,left,right);
    					tree.push(subtree);
    				}
    				op.pop();
    				precedence /= 5;
    			}
    			
    			//if current tok priority <= the op.peek, we must create subtrees until it is not.
    			else if(tok.priority * precedence <= op.peek().priority)
    			{
    				while(tok.priority * precedence <= op.peek().priority)
    				{
    					right = tree.pop();
    					left = tree.pop();
    					root = op.pop();
    					Expression subtree = new Expression(root,left,right);
    					tree.push(subtree);
    				}
    				tok.priority *= precedence;
    				op.push(tok);
    			}
    			
    			//otherwise, multiply tok by precedence (in case of open parentheses before) and push to op stack.
    			else
    			{
    				tok.priority *= precedence;
    				op.push(tok);
    			}
    			
    		}
    	}
    	
    	//while there are still operators, we create subtrees until there are no more operators.    	
    	while(op.peek().token != TokenType.NIL)
    	{
    		right = tree.pop();
    		left = tree.pop();
    		root = op.pop();
    		Expression subtree = new Expression(root,left,right);
    		tree.push(subtree);
    	}
    	
    	//return the resulting tree.
    	return tree.pop();
    	
    }

    	

    // Given a list of tokens corresponding to an infix expression,
    // return its equivalent postfix expression as a list of tokens.
    public static List<Token> infixToPostfix(List<Token> exp) 
    { 
    	//initializing iterator, operator stack, and result list.
    	Iterator <Token> i = exp.iterator(); 
    	ArrayDeque <Token> op = new ArrayDeque<>();
    	Token nil = new Token(TokenType.NIL,0,"");
    	op.push(nil);
    	List <Token> result = new LinkedList<>();
    	//initializing Token and precedence for parentheses
    	Token tok;
    	int precedence = 1;
    	
    	//loop through list of tokens
    	while(i.hasNext())
    	{
    		//tok is current token
    		tok = i.next();
    		
    		//if current token is an operand, add to the postfix expression
    		if(tok.isOperand())
    			result.add(tok);
    		else
    		{
    			//if it is an open parentheses, push to operator stack and set precedence for subsequent tokens
    			if(tok.token == TokenType.OPEN)
    			{
    				op.push(tok);
    				precedence *= 5;
    			}
    			
    			//if it is a closed parentheses, loop through each operator in the stack until open is reached, adding each one to the postfix expression.
    			//also reset precedence and pop the open parentheses.
    			else if(tok.token == TokenType.CLOSE)
    			{
    				while(op.peek().token != TokenType.OPEN)
    				{
    					result.add(op.pop());
    				}
    				precedence /= 5;
    				op.pop();
    			}
    			
    			//if the precedence is <= the operator on the top of the stack, loop through each op in stack satisfying this case and add those operators to the postfix expression
    			//also multiply the current token priority by precedence in case of preceeding open parentheses and push it to the operator stack.
    			else if(tok.priority * precedence <= op.peek().priority)
    			{
    				while(tok.priority * precedence <= op.peek().priority)
    				{
    					result.add(op.pop());
    				}
    				
    				tok.priority *= precedence;
    				op.push(tok);
    				}
    			
    			//if no other conditions are met, then the operator is pushed to the operator stack after multiplying the priority by the precedence.
    			else
    			{
    				tok.priority *= precedence;
    				op.push(tok);
    			}
    		}
    			
    	}
    	
    //Now we add the remaining operators in the stack to the postfix expression and return the postfix expression.
    while(op.peek().token != TokenType.NIL)
    {
    	result.add(op.pop());
    }
	return result;
    }

    // Given a postfix expression, evaluate it and return its value.
    public static long evaluatePostfix(List<Token> exp) 
    {  
    	//initialize operand stack, iterator for list parameter, current token tok, and right, left, and result long variables.
    	ArrayDeque <Token> op = new ArrayDeque<>();
    	Iterator <Token> i = exp.iterator();
    	long right,left,result = 0;
    	Token tok;
    	
    	//loop through list of tokens
    	while(i.hasNext())
    	{
    		//tok = current token
    		tok = i.next();
    		
    		//if the token is an operand, push it to the operand stack
    		if(tok.isOperand())
    		{
    			op.push(tok);
    		}
    		//if the token is an operator, assign two long for the first two operands in the stack and apply the arithmetic given by the token.
    		//it is important to assign the right operand first because stack is LIFO.
    		else
    		{
    			right = op.pop().number;
    			left = op.pop().number;
    			
    			
    			switch(tok.token)
    			{
    				case PLUS:
    					result = left + right;
    					break;
    				case MINUS:
    					result = left - right;
    					break;
    				case TIMES:
    					result = left * right;
    					break;
    				case DIV:
    					result = left/right;
    					break;
    				case POWER:
    					result = (long)Math.pow(left, right);
    					break;
    				case MOD:
    					result = left%right;
    					break;
    				default:
    					break;	
    			}
    			
    			//after arithmetic is calculated, create a new token for the result and push it to the operand stack.
    			Token temp = new Token("" + result);
    			op.push(temp);
    			
    		}
  	
    	}
    	//after looping through all tokens, the resulting operand stack will contain the result.
    	return op.pop().number;
    }

    // Given an expression tree, evaluate it and return its value.
    public static long evaluateExpression(Expression tree) 
    {  
    	//initialize long variables for evaluation left and right subtrees and result variable.
    	long right,left;
    	long result = 0;
    	
    	//if the right or left tree are null, return the current node's value.
    	if(tree.right == null || tree.left == null)
    	{
    		return tree.element.number;
    	}
    	
    	//Otherwise, evaluate right and left subtrees using helper function, and perform the operation indicated by the node.
    	else
    	{
    		right = evaluateExpression(tree.right);
    		left = evaluateExpression(tree.left);
    		switch(tree.element.token)
    		{
    			case PLUS:
    				result = left + right;
    				break;
				case MINUS:
					result = left - right;
					break;
				case TIMES:
					result = left * right;
					break;
				case DIV:
					result = left/right;
					break;
				case POWER:
					result = (long)Math.pow(left, right);
					break;
				case MOD:
					result = left%right;
					break;
				default:
					break;	
    		}
    		//return the result of the operation.
    		return result;
    	}
    }


    // sample main program for testing
    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	
	if (args.length > 0) {
	    File inputFile = new File(args[0]);
	    in = new Scanner(inputFile);
	} else {
	    in = new Scanner(System.in);
	}

	int count = 0;
	while(in.hasNext()) {
	    String s = in.nextLine();
	    List<Token> infix = new LinkedList<>();
	    Scanner sscan = new Scanner(s);
	    int len = 0;
	    while(sscan.hasNext()) {
		infix.add(getToken(sscan.next()));
		len++;
	    }
	    if(len > 0) {
		count++;
		System.out.println("Expression number: " + count);
		System.out.println("Infix expression: " + infix);
		Expression exp = infixToExpression(infix);
		List<Token> post = infixToPostfix(infix);
		System.out.println("Postfix expression: " + post);
		long pval = evaluatePostfix(post);
		long eval = evaluateExpression(exp);
		System.out.println("Postfix eval: " + pval + " Exp eval: " + eval + "\n");
	    }
	}
    }
}
