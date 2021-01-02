package expressivo;

import static org.junit.Assert.*;

/**
 * Represents the variable expression. It may or may not have a value. 
 */
public class VariableExpression implements Expression {

    // AF(r) a character symbol s such that s==symbol.
    // RI : symbol is a case-sensitive nonempty strings of letters
    // safety from rep exposure : the reference of symbol is final, and it points to an immutable object (String). 
    private final String symbol;
    
    public VariableExpression(String symbol){
        this.symbol = symbol;
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isVariable() {
        return true;
    }

    @Override
    public boolean isConstant() {
        return false;
    }
    
    public void checkRep(){     
        assertTrue("this should be a case-sensitive nonempty string of letters", isAlpha(this.symbol));      
    }
    
    /** 
     * check is a string is alphabetic 
     * @param str
     * @return true if str is alphabetic
     */
    public static boolean isAlpha(String str){
        
        return ((!str.equals("")) 
                && (str != null) 
                && (str.matches("^[a-zA-Z]*$"))); 
        
    }

}
