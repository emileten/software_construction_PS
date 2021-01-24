package expressivo.parser;
import expressivo.Expression;
import expressivo.AdditionExpression;
import expressivo.MultiplicationExpression;
import expressivo.ConstantExpression;
import expressivo.VariableExpression;
import java.util.Stack;
//import java.util.List;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;

/** Make a IntegerExpresion value from a parse tree. */
public class MakeExpression implements ExpressionListener {
    private Stack<Expression> stack = new Stack<>();
    // Invariant: stack contains the IntegerExpression value of each parse
    // subtree that has been fully-walked so far, but whose parent has not yet
    // been exited by the walk. The stack is ordered by recency of visit, so that
    // the top of the stack is the IntegerExpression for the most recently walked
    // subtree.
    //
    // At the start of the walk, the stack is empty, because no subtrees have
    // been fully walked.
    //
    // Whenever a node is exited by the walk, the IntegerExpression values of its
    // children are on top of the stack, in order with the last child on top. To
    // preserve the invariant, we must pop those child IntegerExpression values
    // from the stack, combine them with the appropriate IntegerExpression
    // producer, and push back an IntegerExpression value representing the entire
    // subtree under the node.
    //
    // At the end of the walk, after all subtrees have been walked and the the
    // root has been exited, only the entire tree satisfies the invariant's
    // "fully walked but parent not yet exited" property, so the top of the stack
    // is the IntegerExpression of the entire parse tree.

    /**
     * Returns the expression constructed by this listener object.
     * Requires that this listener has completely walked over an IntegerExpression
     * parse tree using ParseTreeWalker.
     * @return IntegerExpression for the parse tree that was walked
     */
    public Expression getExpression() {
        return stack.get(0);
    }

    @Override public void exitRoot(ExpressionParser.RootContext context) {
        // do nothing, root has only one child so its value is 
        // already on top of the stack
    }

    @Override public void exitExpr(ExpressionParser.ExprContext context) {  

        if (context.toString().contains("*")){      
            Expression product = new MultiplicationExpression(stack.pop(), stack.pop());
            stack.push(product);  
        } else if (context.toString().contains("+")){ 
            Expression sum = new AdditionExpression(stack.pop(), stack.pop());
            stack.push(sum);    
        } else if (context.NUMBER() != null) {
            double n = Double.valueOf(context.NUMBER().getText());
            Expression number = new ConstantExpression(n);
            stack.push(number);
        } else if (context.VARIABLE() != null) {
            String s = new String(context.VARIABLE().getText());
            Expression variable = new VariableExpression(s);
            stack.push(variable);
        }
                
        
    }
    
   
//    @Override public void exitPrimitive(ExpressionParser.PrimitiveContext context) {
//        if (context.NUMBER() != null) {
//            // matched the NUMBER alternative
//            double n = Double.valueOf(context.NUMBER().getText());
//            Expression number = new ConstantExpression(n);
//            stack.push(number);
//        } else if (context.VARIABLE() != null) {
//            String s = new String(context.VARIABLE().getText());
//            Expression variable = new VariableExpression(s);
//            stack.push(variable);
//        } else {
//            // matched the '(' expr ')' alternative
//            // do nothing, because sum's value or product's value is already on the stack
//        }
//    }

    @Override public void enterRoot(ExpressionParser.RootContext context) { }
    @Override public void enterExpr(ExpressionParser.ExprContext context) { }
//    @Override public void enterPrimitive(ExpressionParser.PrimitiveContext context) { }

    @Override public void visitTerminal(TerminalNode terminal) { }
    @Override public void enterEveryRule(ParserRuleContext context) { }
    @Override public void exitEveryRule(ParserRuleContext context) { }
    @Override public void visitErrorNode(ErrorNode node) { }         
}
