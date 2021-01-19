package expressivo.parser;
import expressivo.Expression;
import expressivo.AdditionExpression;
import java.util.Stack;
import java.util.List;

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
        // matched the sum | product rule
       
        if(context.sum() != null){

            List<ExpressionParser.SumContext> addends = context.sum();
            assert stack.size() >= addends.size();

            // the pattern above always has at least 1 child;
            // pop the last child
            assert addends.size() > 0;
            Expression sum = stack.pop();

            // pop the older children, one by one, and add them on
            for (int i = 1; i < addends.size(); ++i) {
                sum = new AdditionExpression(stack.pop(), sum);
            }

            // the result is this subtree's Expression
            stack.push(sum);
            
        } else {
            
        }
        
        List<IntegerExpressionParser.PrimitiveContext> addends = context.primitive();
        assert stack.size() >= addends.size();

        // the pattern above always has at least 1 child;
        // pop the last child
        assert addends.size() > 0;
        IntegerExpression expr = stack.pop();

        // pop the older children, one by one, and add them on
        for (int i = 1; i < addends.size(); ++i) {
            expr = new Plus(stack.pop(), expr);
        }

        // the result is this subtree's Expression
        stack.push(expr);
    }    
   
//    @Override public void exitSum(IntegerExpressionParser.SumContext context) {  
//        // matched the primitive ('+' primitive)* rule
//        List<IntegerExpressionParser.PrimitiveContext> addends = context.primitive();
//        assert stack.size() >= addends.size();
//
//        // the pattern above always has at least 1 child;
//        // pop the last child
//        assert addends.size() > 0;
//        IntegerExpression sum = stack.pop();
//
//        // pop the older children, one by one, and add them on
//        for (int i = 1; i < addends.size(); ++i) {
//            sum = new Plus(stack.pop(), sum);
//        }
//
//        // the result is this subtree's IntegerExpression
//        stack.push(sum);
//    }
//
//    @Override public void exitPrimitive(IntegerExpressionParser.PrimitiveContext context) {
//        if (context.NUMBER() != null) {
//            // matched the NUMBER alternative
//            int n = Integer.valueOf(context.NUMBER().getText());
//            IntegerExpression number = new Number(n);
//            stack.push(number);
//        } else {
//            // matched the '(' sum ')' alternative
//            // do nothing, because sum's value is already on the stack
//        }
//    }
//
//    // don't need these here, so just make them empty implementations
//    @Override public void enterRoot(IntegerExpressionParser.RootContext context) { }
//    @Override public void enterSum(IntegerExpressionParser.SumContext context) { }
//    @Override public void enterPrimitive(IntegerExpressionParser.PrimitiveContext context) { }
//
//    @Override public void visitTerminal(TerminalNode terminal) { }
//    @Override public void enterEveryRule(ParserRuleContext context) { }
//    @Override public void exitEveryRule(ParserRuleContext context) { }
//    @Override public void visitErrorNode(ErrorNode node) { }         
}
