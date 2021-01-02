package expressivo;

/**
 * Represents the product of two expressions.
 */
public class MultiplicationExpression implements Expression {

    // AF(r) two expressions e1 and e2 such that expr1==e1 and expr2==e2
    // safety from rep exposure : the references are final, and point to immutable objects (Expression). 
    
    private final Expression expr1;
    private final Expression expr2;
    
    public MultiplicationExpression(Expression expr1, Expression expr2){
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

    

//  /**
//   * Sum two expressions. 
//   * @param expr1 Expression 
//   * @param expr2 Expression 
//   * @return the sum of expr1 and expr2
//   */
//  public Expression isEmpty(Expression expr1, Expression expr2);

    
}
