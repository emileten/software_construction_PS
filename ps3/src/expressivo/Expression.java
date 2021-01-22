/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionListener;
import expressivo.parser.ExpressionParser;
import expressivo.parser.PrintEverything;
import expressivo.parser.MakeExpression;


/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    // Expression = Empty + Constant(double) + Variable(String) + Addition(Expression,Expression) + Multiplication(Expression, Expression) 
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {

        // Step 1 : convert an ANTLR grammar file into a set of java classes that implement the grammar. 
        // <Reader : grammar file -> classes>
        
        // Step 2 : use the lexer class extension created above to convert a stream of characters (your string 'input') into a stream of
        // terminals (called tokens in ANTLR language). This function is called a 'lexer' in ANTLR. 
        // <Lexer : stream of characters -> stream of terminals>
        
        // Step 3 : use parser class extension created above to convert the set of terminals into a 'parse tree'. 
        // A tree describes your string in terms of your grammar's terminals, non terminals and productions. 
        // <Parser : stream of terminals -> parse tree>
        
        // Step 4 : use the parse tree to create an object from your class, using a 'parse tree converter':
        // <ParseTreeToExpression : parse tree -> Expression> 
        // But this wraps several things. It requires that you 'walk' through the parse tree, and that something 'listens' to this walk. 
        // - a specific class was created for the walk, a parsing tree walker.
        // - an extension of a listener interface was created for you/
        
        // Step 1 : outside of this implementation ! 
        
        // Step 2 : string -> tokens 
        CharStream stream = new ANTLRInputStream(input);
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        
        
        // Step 3 tokens -> parse tree 
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();
        ParseTree tree = parser.root(); // Generate the parse tree using the starter rule. Here the starter rule is root. 
        System.err.println(tree.toStringTree(parser)); // *** Debugging option #1: print the tree to the console
        Trees.inspect(tree, parser); // *** Debugging option #2: show the tree in a window

                           
        // Step 4 : parse tree -> expression 
        // substep 1 : 'warm up', i.e, walk a first time through the tree, without creating an Expression object, just printing stuff. 
        ParseTreeWalker walker = new ParseTreeWalker(); // create a walker object
        ExpressionListener listener = new PrintEverything(); // create a listener object 
        walker.walk(listener, tree); // walk through the tree, and let the listener listen. 
        // substep 2 : actually create the object while walking and listening to the walk ! 
        MakeExpression exprMaker = new MakeExpression(); // this is an expression creator object
        new ParseTreeWalker().walk(exprMaker, tree); // the expression creator listens to the walk and stores the information 
        return exprMaker.getExpression(); // get the object the creator constructed
        
    }
    
    
  
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     * initial whitespaces and parentheses are ignored, and the output 
     * surrounds + with spaces but not *
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    /** Check if this expression is a symbolic variable.
    * @return true if this Expression is a symbolic variable. 
    */
    public boolean isVariable();
    
    
    /** Check if this expression is a constant double. 
    * @return true this Expression is a constant double. 
    */
    public boolean isConstant();
    
        
    // TODO more instance methods
    
}