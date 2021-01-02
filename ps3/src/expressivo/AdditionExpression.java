package expressivo;

/**
 * Represents the sum of two expressions.
 */
public class AdditionExpression implements Expression {

    // AF(r) two expressions e1 and e2 such that expr1==e1 and expr2==e2
    // safety from rep exposure : the references are final, and point to immutable objects (Expression). 
    
    private final Expression expr1;
    private final Expression expr2;
    
    public AdditionExpression(Expression expr1, Expression expr2){
        this.expr1 = expr1;
        this.expr2 = expr2;
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
    
    
//    /**
//    * Sum two expressions. 
//    * @param expr1 Expression 
//    * @param expr2 Expression 
//    * @return the sum of expr1 and expr2
//    */
//    public Expression sum(Expression expr1, Expression expr2){
//        return expr1 + expr2;
//    }
// 
 

}
