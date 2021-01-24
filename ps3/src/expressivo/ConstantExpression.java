package expressivo;
import static org.junit.Assert.*;
import java.math.BigDecimal;

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
//    @Override
//    public boolean isVariable() {
//        return false;
//    }
//
//    @Override
//    public boolean isConstant() {
//        return true;
//    }
    
    public void checkRep(){        
        assertTrue("this should be a nonnegative integer or floating point number", this.value>=0);        
    }
    
    @Override 
    public String toString(){        
        return String.valueOf(value);        
    }
    
    @Override 
    public boolean equals(Object thatObject){        
        if (thatObject instanceof ConstantExpression){
            BigDecimal bigThis = new BigDecimal(this.value).stripTrailingZeros();
            BigDecimal bigThat = new BigDecimal(((ConstantExpression) thatObject).value).stripTrailingZeros();
            return bigThis==bigThat;                        
        } else {
            return false;
        }        
    }
    
    @Override 
    public int hashCode(){       
       BigDecimal bigThis = new BigDecimal(this.value).stripTrailingZeros();       
       return bigThis.intValue();       
    }

}
