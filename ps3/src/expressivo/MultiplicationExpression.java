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
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override 
    public String toString(){
        String multiplicationString = new String(expr1.toString() + "*" + expr2.toString());
        return multiplicationString;
    }
    
    @Override 
    public boolean equals(Object thatObject){        
        if (thatObject instanceof MultiplicationExpression){
            if (((MultiplicationExpression) thatObject).expr1==this.expr1 && ((MultiplicationExpression) thatObject).expr2==this.expr2){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }        
    }
    
    
    @Override
    public int hashCode(){
        return expr1.hashCode()*31 + expr2.hashCode();
    }

//  /**
//   * Sum two expressions. 
//   * @param expr1 Expression 
//   * @param expr2 Expression 
//   * @return the sum of expr1 and expr2
//   */
//  public Expression isEmpty(Expression expr1, Expression expr2);

    
}
