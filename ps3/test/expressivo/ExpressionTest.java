/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy 
    
    // parse()
    // single number :  5
    // single number, with decimals : 5.45 
    // single number with useless zeros decimals : 5.00
    // single variable : x
    // addition of two numbers : 5+7
    // addition of two variables : x+y 
    // addition of number and variable : 5+y
    // same as above but for product : 5*y
    // combination of product and addition with symbols and numbers and parenthesis : (5+7*y)+10.45*x
    // same as above but with spaces ( 5 + 7 * y ) + 10.45 * x
    
    // HashCode() and equals()
    // 'general case' : x*2*3 + y != x*6 + y + 0.2
    // 1+x != x+1
    // 1+x == (1+x)
    // 1*x == (1*x)
    // 1+x == (1) + (x)
    // 3 + 4 + 5 == (3+4+5)
    // x + 1 == x + 1.000
    // (3 + 4) + 5 == 3 + (4+5)
    
    // toString()
    // (x * 6) + (y + 0.2) --> "x*6 + y + 0.2"
    
    // isEmpty()
    // "" -> empty
    // "x*3" -> non-empty
    
    // isVariable()
    // x --> true
    // y --> true
    // 3 --> false
    // x*3 --> false
    
    // isConstant()
    // 3 --> true
    // 4 --> true 
    // 3 + 4 --> false 
    // x*3 --> false
    
    // isAlpha()
    // "123" --> FALSE
    // "ab123" --> FALSE
    // "x" --> TRUE
    // "X" --> TRUE
    // "XYZabcd" --> TRUE
    // "@#$@#%;;e" --> FALSE

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testExpressionParse() {        
   
        Expression.parse("5");
        Expression.parse("5.45");
        Expression.parse("5.00");
        Expression.parse("x");
        Expression.parse("5+7");
        Expression.parse("x+y");
        Expression.parse("5+y");
        Expression.parse("5*y");
        Expression.parse("(5+7*y)+10.45*x");
        Expression.parse("( 5 + 7 * y ) + 10.45 * x");
        
    }
    
    @Test
    public void testExpressionEquals() {        
        assertTrue("expected x*2*3 + y != x*6 + y + 0.2", !Expression.parse("x*2*3 + y").equals(Expression.parse("x*6 + y + 0.2")));        
        assertTrue("expected 1+x != x+1", !Expression.parse("1+x").equals(Expression.parse("x+1")));
        assertTrue("expected 1+x == (1+x)", Expression.parse("1+x").equals(Expression.parse("(1+x)")));
        assertTrue("expected 1*x == (1*x)", Expression.parse("1*x").equals(Expression.parse("(1*x)")));
        assertTrue("expected 1+x == (1) + (x)", Expression.parse("1+x").equals(Expression.parse("(1) + (x)")));
        assertTrue("expected 3 + 4 + 5 == (3+4+5)", Expression.parse("3 + 4 + 5").equals(Expression.parse("(3+4+5)")));
        assertTrue("expected x+1==x+1.000", Expression.parse("x+1").equals(Expression.parse("x+1.000")));
        assertTrue("expected (3 + 4) + 5 == 3+(4+5)", Expression.parse("(3+4) + 5").equals(Expression.parse("3+(4+5)")));
    } 
    
    @Test
    public void testExpressionHashCode() {        
        assertTrue("expected hash code of (3 + 4) + 5 to be equal to hash code of 3+(4+5)", Expression.parse("(3+4) + 5").hashCode()==Expression.parse("3+(4+5)").hashCode());
    } 
    
    @Test
    public void testExpressionToString() { 
        assertTrue("expected String version of pasrsed '(x*6) + (y + 0.2)' to be equal to 'x*6 + y + 0.2'", Expression.parse("(x * 6) + (y + 0.2)").toString()=="x*6 + y + 0.2");               
    } 
    
    @Test
    public void testExpressionIsEmpty() {   
        assertTrue("this should be an empty expression", Expression.parse("").isEmpty());
        assertFalse("this should be a non-empty expression", Expression.parse("x*3").isEmpty());      
    } 
    
    @Test
    public void testExpressionIsVariable() {        
        assertTrue("this should be a variable", Expression.parse("x").isEmpty());
        assertTrue("this should be a variable", Expression.parse("y").isEmpty());
        assertFalse("this should not be a variable", Expression.parse("3").isEmpty());
        assertFalse("this should not be a variable", Expression.parse("x*3").isEmpty());   
    } 
    
    @Test
    public void testExpressionIsConstant() {        
        assertTrue("this should be a constant", Expression.parse("3").isEmpty());
        assertTrue("this should be a constant", Expression.parse("4").isEmpty());
        assertFalse("this should not be a constant", Expression.parse("3+4").isEmpty());
        assertFalse("this should not be a constant", Expression.parse("x*3").isEmpty());            
    } 
    
    
    @Test
    public void testIsAlpha() {         
        assertTrue("'123' isn't alphabetic", VariableExpression.isAlpha("123"));
        assertTrue("'ab123' isn't alphabetic", VariableExpression.isAlpha("ab123"));
        assertTrue("'x' is alphabetic", VariableExpression.isAlpha("x"));
        assertTrue("'X' is alphabetic", VariableExpression.isAlpha("X"));
        assertTrue("'XYZabcd' is alphabetic", VariableExpression.isAlpha("XYZabcd"));
        assertTrue("'@#$@#%;;e' isn't alphabetic", VariableExpression.isAlpha("@#$@#%;;e"));               
    } 
    
    // TODO tests for Expression
    
}
