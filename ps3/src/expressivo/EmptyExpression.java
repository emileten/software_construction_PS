package expressivo;

/**
 * Represents the empty Expression. The empty Expression is equivalent to zero. 
 */
public class EmptyExpression implements Expression {

    public EmptyExpression(){
        
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

}
