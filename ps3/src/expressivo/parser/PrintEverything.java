package expressivo.parser;


import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;

public class PrintEverything implements ExpressionListener {

    @Override public void enterRoot(ExpressionParser.RootContext context) {
        System.err.println("entering root");
    }
    @Override public void exitRoot(ExpressionParser.RootContext context) {
        System.err.println("exiting root");
    }
    
    @Override public void enterExpr(ExpressionParser.ExprContext context) {
        System.err.println("entering expr");
    }
    @Override public void exitExpr(ExpressionParser.ExprContext context) {
        System.err.println("exiting expr");
    }

    @Override public void enterPrimitive(ExpressionParser.PrimitiveContext context) {
        System.err.println("entering primitive");
    }
    @Override public void exitPrimitive(ExpressionParser.PrimitiveContext context) {
        System.err.println("exiting primitive");
    }

    @Override public void visitTerminal(TerminalNode terminal) {
        System.err.println("terminal " + terminal.getText());            
    }

    // don't need these here, so just make them empty implementations
    @Override public void enterEveryRule(ParserRuleContext context) { }
    @Override public void exitEveryRule(ParserRuleContext context) { }
    @Override public void visitErrorNode(ErrorNode node) { } 
    
}
