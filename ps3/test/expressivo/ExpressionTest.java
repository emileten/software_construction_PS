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
    
    // HashCode() and equals()
    // 'general case' : x*2*3 + y != x*6 + y - 0.2
    // 1+x != x+1
    // 1+x == (1+x)
    // 1*x == (1*x)
    // 1+x == (1) + (x)
    // 3 + 4 + 5 == (3+4+5)
    // x + 1 == x + 1.000
    // (3 + 4) + 5 == 3 + (4+5)
    
    // toString()
    // empty expression 
    // +
    // * 
    // +, *
    // +, *, ()
    // +, *, (), variables
    // +, *, (), variables, doubles with decimals
    
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
    public void testExpressionEquals() { 
        
        assertTrue("expected x*2*3 + y != x*6 + y - 0.2", !Expression.parse("x*2*3 + y").equals(Expression.parse("x*6 + y - 0.2")));        
        assertTrue("expected 1+x != x+1", !Expression.parse("1+x").equals(Expression.parse("x+1")));
        assertTrue("expected 1+x == (1+x)", !Expression.parse("1+x").equals(Expression.parse("(1+x)")));
        assertTrue("expected 1*x == (1*x)", !Expression.parse("1*x").equals(Expression.parse("(1*x)")));
        assertTrue("expected 1+x == (1) + (x)", !Expression.parse("1+x").equals(Expression.parse("(1) + (x)")));
        assertTrue("expected 3 + 4 + 5 == (3+4+5)", !Expression.parse("3 + 4 + 5").equals(Expression.parse("(3+4+5)")));
        assertTrue("expected x+1==x+1.000", !Expression.parse("x+1").equals(Expression.parse("x+1.000")));
        assertTrue("expected (3 + 4) + 5 == 3+(4+5)", !Expression.parse("(3+4) + 5").equals(Expression.parse("3+(4+5)")));

    } 
    
    @Test
    public void testExpressionHashCode() { 
        
        assertTrue("expected hash code of (3 + 4) + 5 to be equal to hash code of 3+(4+5)", Expression.parse("(3+4) + 5").hashCode()!=Expression.parse("3+(4+5)").hashCode());

    } 
    
    @Test
    public void testExpressionToString() { 
   
        String StrExpr = "x*6 + y - 0.2";
        Expression Expr = Expression.parse(StrExpr);
        assertTrue("expected parsed String version of the x*6 + y - 0.2 expression to be equal to that expression", Expr.equals(Expression.parse(StrExpr)));
        
        
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
