package expressivo;
import static org.junit.Assert.*;

/**
 * This class represents constant values in a mathematical algebraic expression. 
 */
public class ConstantExpression implements Expression {

    // AF(r) an integer or floating point number n such that n==value.
    // RI : value is a nonnegative integer or floating-point number
    // safety from rep exposure : the reference of value is final, and it points to an immutable object (double). 
    private final double value; 
    
    public ConstantExpression(double value){
        this.value = value;
    }
    @Override
    public boolean isEmpty() {
        return value==0;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return true;
    }
    
    public void checkRep(){        
        assertTrue("this should be a nonnegative integer or floating point number", this.value>=0);        
    }

}
